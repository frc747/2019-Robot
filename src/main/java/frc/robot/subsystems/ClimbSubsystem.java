/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.DoNothing;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
/**
 * Add your docs here.
 */
public class ClimbSubsystem extends Subsystem {
  

  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;

  public TalonSRX winch1 = new TalonSRX(3);
  public TalonSRX winch2 = new TalonSRX(4);
  public TalonSRX winch3 = new TalonSRX(7);
  public TalonSRX winch4 = new TalonSRX(8);

  public TalonSRX latch = new TalonSRX(11);
  @Override
  public void initDefaultCommand() {
    latch.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);

    setDefaultCommand(new DoNothing());
    winch2.set(ControlMode.Follower, winch1.getDeviceID());
    winch4.set(ControlMode.Follower, winch3.getDeviceID());
  }

  public void setLeftWinch(double speed) {
    winch1.set(ControlMode.PercentOutput, speed);
  }

  public void setRightWinch(double speed) {
    winch3.set(ControlMode.PercentOutput, -speed);
  }

  public void setWinches(double speed) {
    winch1.set(ControlMode.PercentOutput, speed);
    winch3.set(ControlMode.PercentOutput, -speed);
  }
  // public void setCrank(double speed) {
  //   crank.set(ControlMode.PercentOutput, speed);
  // }
}
