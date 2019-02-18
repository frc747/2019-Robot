/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ClimbCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
/**
 * Add your docs here.
 */
public class ClimbSubsystem extends Subsystem {
  
  public TalonSRX winch1 = new TalonSRX(3);
  public TalonSRX winch2 = new TalonSRX(4);
  public TalonSRX crank = new TalonSRX(5);


  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ClimbCommand());
    winch2.set(ControlMode.Follower, winch1.getDeviceID());
  }


  public void setWinch(double speed) {
    winch1.set(ControlMode.PercentOutput, speed);
  }

  public void setCrank(double speed) {
    crank.set(ControlMode.PercentOutput, speed);
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
}
