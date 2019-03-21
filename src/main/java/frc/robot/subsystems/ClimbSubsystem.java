/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.ClimbCommandSafe;
import frc.robot.commands.DoNothing;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
/**
 * Add your docs here.
 */
public class ClimbSubsystem extends Subsystem {
  public ClimbSubsystem() {

    winch1.restoreFactoryDefaults();
    winch2.restoreFactoryDefaults();
    winch1.setOpenLoopRampRate(0.0);
    winch2.setOpenLoopRampRate(0.0);
    winch1.setSmartCurrentLimit(45);
    winch2.setSmartCurrentLimit(45);

    winch1.setInverted(false);
    winch2.setInverted(false);


    winch3.restoreFactoryDefaults();
    winch4.restoreFactoryDefaults();
    winch3.setOpenLoopRampRate(0.0);
    winch4.setOpenLoopRampRate(0.0);
    winch3.setSmartCurrentLimit(45);
    winch4.setSmartCurrentLimit(45);

    winch3.setInverted(false);
    winch4.setInverted(false);
  }
  

  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;

  public CANSparkMax winch1 = new CANSparkMax(3, MotorType.kBrushless);
  public CANSparkMax winch2 = new CANSparkMax(4, MotorType.kBrushless);
  public CANSparkMax winch3 = new CANSparkMax(7, MotorType.kBrushless);
  public CANSparkMax winch4 = new CANSparkMax(8, MotorType.kBrushless);

  // public TalonSRX winch1 = new TalonSRX(3);
  // public TalonSRX winch2 = new TalonSRX(4);
  // public TalonSRX winch3 = new TalonSRX(7);
  // public TalonSRX winch4 = new TalonSRX(8);

  public TalonSRX latch = new TalonSRX(11);
  @Override
  public void initDefaultCommand() {
    
    latch.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);
    latch.setInverted(true);
    latch.setSensorPhase(true);
    setDefaultCommand(new ClimbCommand());
    // winch2.follow(winch1);
    // winch4.follow(winch3);
    // winch2.set(ControlMode.Follower, winch1.getDeviceID());
    // winch4.set(ControlMode.Follower, winch3.getDeviceID());
  }

  public void setLeftWinch(double speed) {
    winch1.set(speed);
    winch2.set(speed);
    //winch1.set(ControlMode.PercentOutput, speed);
  }

  public void setRightWinch(double speed) {
    winch3.set(speed);
    winch4.set(speed);
    //winch3.set(ControlMode.PercentOutput, -speed);
  }

  public void setWinches(double speed) {
    winch1.set(speed);
    winch2.set(speed);
    winch3.set(speed);
    winch4.set(speed);
    // winch1.set(ControlMode.PercentOutput, speed);
    // winch3.set(ControlMode.PercentOutput, -speed);
  }

  public void resetLatchEncoder() {
    latch.setSelectedSensorPosition(0);
  }

  public void changeClimbBrakeMode(boolean enabled) {
    if (enabled) {
      winch1.setIdleMode(IdleMode.kBrake);
      winch2.setIdleMode(IdleMode.kBrake);
      winch3.setIdleMode(IdleMode.kBrake);
      winch4.setIdleMode(IdleMode.kBrake);
    } else {
      winch1.setIdleMode(IdleMode.kCoast);
      winch2.setIdleMode(IdleMode.kCoast);
      winch3.setIdleMode(IdleMode.kCoast);
      winch4.setIdleMode(IdleMode.kCoast);
    }

  }
  // public void setCrank(double speed) {
  //   crank.set(ControlMode.PercentOutput, speed);
  // }
}
