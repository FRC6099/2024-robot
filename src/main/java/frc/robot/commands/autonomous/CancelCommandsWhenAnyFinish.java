// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;

public class CancelCommandsWhenAnyFinish extends Command {
  private final Command[] commands;
  private boolean hasCancelledCommands = false;
  /** Creates a new CancelCommandsWhenAnyFinish. */
  public CancelCommandsWhenAnyFinish(Command... commands) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.commands = commands;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hasCancelledCommands = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean hasACommandFinished = false;
    for (Command command : commands) {
      if (command.isFinished()) {
        hasACommandFinished = true;
      }
    }
    if (hasACommandFinished) {
        hasCancelledCommands = true;
      for (Command command : commands) {
        command.cancel();
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return hasCancelledCommands;
  }
}
