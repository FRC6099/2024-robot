// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.enums.ArmPosition;
import frc.robot.subsystems.Intake;

public class RetractIntakeArm extends Command {
  private final Intake intake;
  private final Timer timer;
  private final double duration;
  /** Creates a new OperateArm. */
  public RetractIntakeArm(Intake intake) {
    this(intake, 3.0);
  }
  public RetractIntakeArm(Intake intake, double duration) {
    this.intake = intake;
    this.timer = new Timer();
    this.duration = duration;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!isFinished()) {
      intake.moveArm(-0.5);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.stopArm();
    timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return ArmPosition.HOME == intake.getArmPosition();
    return timer.hasElapsed(duration);
  }
}
