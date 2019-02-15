/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
//import frc.robot.commands.ElevatorCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Add your docs here.
 */
public class ElevatorSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static final double WHEEL_CIRCUMFERNCE = 20.125;
  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;
  private static final double GEAR_RATIO_MULTIPLIER = 1;
  private static final double ENCODER_TICKS = 4096;

  public TalonSRX elevator1 = new TalonSRX(50); // get actual id later
  public TalonSRX elevator2 = new TalonSRX(51); //Get actual id later
  
  public double convertInchesToRevs(double inches) {
    return inches / WHEEL_CIRCUMFERNCE;
}

public void set(double left, double right) {
    	
  elevator2.set(ControlMode.PercentOutput, left);
  elevator1.set(ControlMode.PercentOutput, right);
}

public void stop() {
  ControlMode mode = elevator2.getControlMode();

  double left = 0;
  double right = 0;
  
  switch (mode) {
  case MotionMagic:
      left = elevator2.getSelectedSensorPosition(pidIdx);
      right = elevator1.getSelectedSensorPosition(pidIdx);
      break;
  case PercentOutput:
  case Velocity:
  case Follower:
  default:
      // Values should be 0;
      break;
  }
  
  this.set(left, right);
  }

  public void resetBothEncoders(){
        this.enableVBusControl();
    	this.elevator1.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    	this.elevator2.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

  public double convertRevsToInches(double revs) {
    return revs * WHEEL_CIRCUMFERNCE;
}

  public double convertTicksToInches(double ticks) {
    return convertRevsToInches(convertTicksToRevs(ticks));
}

  public double convertRevsToTicks(double revs) {
    return revs * ENCODER_TICKS;
}

public double convertTicksToRevs(double ticks) {
    return ticks / ENCODER_TICKS;
}
  public double averageInchesDriven() {
    return convertTicksToInches(undoGearRatio(getCombindedEncoderPosition()));
}

  public double applyGearRatio(double original) {
    return original * GEAR_RATIO_MULTIPLIER;
}

public double undoGearRatio(double original) {
    return original / GEAR_RATIO_MULTIPLIER;
}

  public void enableVBusControl() {
    this.changeControlMode(ControlMode.PercentOutput);
  }

  public void changeControlMode(ControlMode mode) {
    elevator2.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    elevator1.setSelectedSensorPosition(0, pidIdx, timeoutMs);
      elevator2.set(mode, 0);
      elevator1.set(mode, 0);
  }

  public void enablePositionControl() {
    this.changeControlMode(ControlMode.MotionMagic);
  }

  public void setPID(double leftTicks, double rightTicks) {
    elevator2.set(ControlMode.MotionMagic, leftTicks);
    elevator1.set(ControlMode.MotionMagic, rightTicks);
  }

  public double getLeftPosition() {
        return elevator2.getSelectedSensorPosition(pidIdx);
    }
        
  public double getRightPosition() {
        return elevator1.getSelectedSensorPosition(pidIdx);
    }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public double getLeftEncoderPosition() {
    return elevator2.getSelectedSensorPosition(pidIdx);
}

public double getRightEncoderPosition() {
    return elevator1.getSelectedSensorPosition(pidIdx);
}

  public double getCombindedEncoderPosition() {
    return (getLeftEncoderPosition() + getRightEncoderPosition()) / 2;
}
}
