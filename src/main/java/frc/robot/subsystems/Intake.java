// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkAnalogSensor;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.enums.ArmPosition;

public class Intake extends SubsystemBase {

  private static final double ARM_VELOCITY = 1.0;

  private final CANSparkMax armMotor = new CANSparkMax(Constants.INTAKE_ARM_MOTOR_CAN_ID, MotorType.kBrushed);
  private final SparkPIDController armMotorController = armMotor.getPIDController();
  private final RelativeEncoder armEncoder = armMotor.getAlternateEncoder(4096);
  private final WPI_TalonSRX grabberMotor = new WPI_TalonSRX(Constants.INTAKE_GRABBER_MOTOR_CAN_ID);
  private final DigitalInput noteLimitSwitch = new DigitalInput(Constants.NOTE_LIMIT_SWITCH);

  /** Creates a new Intake. */
  public Intake() {
    // TODO: Configure encoder
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // https://docs.revrobotics.com/brushless/spark-max/revlib/migrating

  public void moveArmForward(double speed) {
    double positiveSpeed = Math.abs(speed);
    if (getArmPosition() != ArmPosition.EXTENDED) {
      moveArm(positiveSpeed);
    }
  }

  public void moveArmBackward(double speed) {
    double negativeSpeed = -1.0 * Math.abs(speed);
    if (getArmPosition() != ArmPosition.HOME) {
      moveArm(negativeSpeed);
    }
  }

  public void moveArm(ArmPosition position) {
    if (ArmPosition.HOME == position) {
      armMotor.set(0.2);
      // armMotorController.setReference(0, ControlType.kPosition);
    } else if (ArmPosition.EXTENDED == position) {
      armMotor.set(-0.2);
      armMotorController.setReference(90, ControlType.kPosition);
    }
  }

  public void moveArm(double speed) {
    armMotor.set(speed);
    // armMotorController.setReference(speed * ARM_VELOCITY, ControlType.kVelocity);
  }

  public void stopArm() {
    armMotor.set(0.0);
    // armMotorController.setReference(0.0, ControlType.kVoltage);
  }

  public void inject() {
    grabberMotor.set(ControlMode.PercentOutput, 0.75);
  }

  public void eject() {
    grabberMotor.set(ControlMode.PercentOutput, -0.75);
  }

  public void stopIntake() {
    grabberMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public ArmPosition getArmPosition() {
    SmartDashboard.putNumber("Arm ticks", armEncoder.getPosition());
    // TODO: Find actual positions
    if (armEncoder.getPosition() < 100.0) {
      return ArmPosition.HOME;
    } else if (armEncoder.getPosition() > 1000.0) {
      return ArmPosition.EXTENDED;
    }
    return ArmPosition.OUT_OF_POSITION;
  }

  public boolean isNotePresent() {
    return !noteLimitSwitch.get();
  }
}
