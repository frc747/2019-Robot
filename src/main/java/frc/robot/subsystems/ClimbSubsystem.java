/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ClimbCommand;
/**
 * Add your docs here.
 */
public class ClimbSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public TalonSRX climberWinchPrimary = new TalonSRX(100);
  public TalonSRX climberWinchBack = new TalonSRX(101);
  public TalonSRX climberCrank = new TalonSRX(102);

  public double maxThresholdWinch;
  public double maxThresholdCrank;

  public ClimbSubsystem() {
    climberWinchBack.set(ControlMode.Follower, climberWinchPrimary.getDeviceID());
  }

  public void setWinch(double value) {
    
    // double out = value;  
    
    // if (Math.abs(out) >= maxThresholdWinch) {
    //   if (out < 0) {
    //     out = -maxThresholdWinch;
    //   } else {
    //     out = maxThresholdWinch;

    //   }
    // }
    
    climberWinchPrimary.set(ControlMode.PercentOutput, value);
}

  public void setCrank(double value) {
    // double out = value;

    // if (out >= maxThresholdCrank) {
    //   out = maxThresholdCrank;
    // }

    climberCrank.set(ControlMode.PercentOutput, value);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new ClimbCommand(maxThresholdWinch, maxThresholdCrank));
  }
}
 