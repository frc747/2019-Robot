/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.LiftToPosition;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Add your docs here.
 */
public class LiftSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  int timeoutMs = 10;

  private static final int pidIdx = 0;

  private static final double MAX_OUTPUT = 1.0;
  private static final double MIN_OUTPUT = 0.0;

  public TalonSRX liftPrimary = new TalonSRX(20);
  public TalonSRX liftSecondary = new TalonSRX(21);

  public TalonSRX shoulderPrimary = new TalonSRX(22);
  public TalonSRX shoulderSecondary = new TalonSRX(23);

  public TalonSRX wristPrimary = new TalonSRX(24);

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LiftToPosition(0, 0));

    liftPrimary.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);
    shoulderPrimary.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);
    wristPrimary.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);

    liftSecondary.set(ControlMode.Follower, liftPrimary.getDeviceID());
    shoulderSecondary.set(ControlMode.Follower, shoulderPrimary.getDeviceID());

    
    liftPrimary.configNominalOutputForward(+MIN_OUTPUT, timeoutMs);
    liftPrimary.configNominalOutputReverse(-MIN_OUTPUT, timeoutMs);
    liftPrimary.configPeakOutputForward(+MAX_OUTPUT, timeoutMs);
    liftPrimary.configPeakOutputReverse(-MAX_OUTPUT, timeoutMs);

    shoulderPrimary.configNominalOutputForward(+MIN_OUTPUT, timeoutMs);
    shoulderPrimary.configNominalOutputReverse(-MIN_OUTPUT, timeoutMs);
    shoulderPrimary.configPeakOutputForward(+MAX_OUTPUT, timeoutMs);
    shoulderPrimary.configPeakOutputReverse(-MAX_OUTPUT, timeoutMs);
    
    wristPrimary.configNominalOutputForward(+MIN_OUTPUT, timeoutMs);
    wristPrimary.configNominalOutputReverse(-MIN_OUTPUT, timeoutMs);
    wristPrimary.configPeakOutputForward(+MAX_OUTPUT, timeoutMs);
    wristPrimary.configPeakOutputReverse(-MAX_OUTPUT, timeoutMs);
  }
// 2 lift, 2 shoulder, 1 wrist
  public void resetLiftEncoder() {
    liftPrimary.setSelectedSensorPosition(0);    
  }

  public void resetShoulderEncoder() {
    shoulderPrimary.setSelectedSensorPosition(0);    
  }
  public void resetWristEncoder() {
    wristPrimary.setSelectedSensorPosition(0);    
  }

  public double getLiftPosition() {
    return liftPrimary.getSelectedSensorPosition();
  }
  
  public double getShoulderPosition() {
    return shoulderPrimary.getSelectedSensorPosition();
  }

  public double getWristPosition() {
    return wristPrimary.getSelectedSensorPosition();
  }

  public void setLift(double speed) {
    liftPrimary.set(ControlMode.PercentOutput, speed);
  }

  public void setShoulder(double speed) {
    shoulderPrimary.set(ControlMode.PercentOutput, speed);
  }

  public void setWrist(double speed) {
    wristPrimary.set(ControlMode.PercentOutput, speed);
  }

  public void changeControlMode(ControlMode mode, TalonSRX talon) {
    talon.set(mode, 0);
  } 
  
  public void enablePositionControl(TalonSRX talon) {
      this.changeControlMode(ControlMode.MotionMagic, talon);
  }

  public void enableVBusControl(TalonSRX talon) {
      this.changeControlMode(ControlMode.PercentOutput, talon);
  }

  public void setPID(double leftTicks, double rightTicks, TalonSRX talon) {
    talon.set(ControlMode.MotionMagic, leftTicks);
  }

  public void stop(TalonSRX talon) {
    ControlMode mode = talon.getControlMode();

    switch (mode) {
    case MotionMagic:
        break;
    case PercentOutput:
    case Velocity:
    case Follower:
    default:
        // Values should be 0;
        break;
    }
    
    talon.set(ControlMode.PercentOutput, talon.getSelectedSensorPosition(pidIdx));
}
}
