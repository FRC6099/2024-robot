// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

  private final CANSparkFlex leftShooterMotor = new CANSparkFlex(Constants.LEFT_SHOOTER_ARM_MOTOR_CAN_ID, MotorType.kBrushless);
  private final CANSparkFlex rightShooterMotor = new CANSparkFlex(Constants.RIGHT_SHOOTER_ARM_MOTOR_CAN_ID, MotorType.kBrushless);
  private final Timer timer = new Timer();

  /** Creates a new Shooter. */
  public Shooter() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void prime() {
    timer.start();
    leftShooterMotor.getPIDController().setReference(100.0, ControlType.kVelocity);
    rightShooterMotor.getPIDController().setReference(100.0, ControlType.kVelocity);
  }

  public void stop() {
    timer.stop();
    timer.reset();
    leftShooterMotor.getPIDController().setReference(0.0,ControlType.kVoltage);
    rightShooterMotor.getPIDController().setReference(0.0,ControlType.kVoltage);
  }

  public boolean isPrimed() {
    return timer.hasElapsed(1.0);
  }
}