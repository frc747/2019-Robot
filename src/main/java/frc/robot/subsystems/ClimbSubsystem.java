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
  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;

  public TalonSRX latch = new TalonSRX(11);

  public CANSparkMax leftClimbPrimary = new CANSparkMax(3, MotorType.kBrushless);
  public CANSparkMax leftClimbSecondary = new CANSparkMax(4, MotorType.kBrushless);
  public CANSparkMax rightClimbPrimary = new CANSparkMax(7, MotorType.kBrushless);
  public CANSparkMax rightClimbSecondary = new CANSparkMax(8, MotorType.kBrushless);

  public ClimbSubsystem() {

    leftClimbPrimary.restoreFactoryDefaults();
    leftClimbSecondary.restoreFactoryDefaults();
    leftClimbPrimary.setOpenLoopRampRate(0.0);
    leftClimbSecondary.setOpenLoopRampRate(0.0);
    leftClimbPrimary.setSmartCurrentLimit(45);
    leftClimbSecondary.setSmartCurrentLimit(45);

    leftClimbPrimary.setInverted(false);
    leftClimbSecondary.setInverted(false);


    rightClimbPrimary.restoreFactoryDefaults();
    rightClimbSecondary.restoreFactoryDefaults();
    rightClimbPrimary.setOpenLoopRampRate(0.0);
    rightClimbSecondary.setOpenLoopRampRate(0.0);
    rightClimbPrimary.setSmartCurrentLimit(45);
    rightClimbSecondary.setSmartCurrentLimit(45);

    rightClimbPrimary.setInverted(true);
    rightClimbSecondary.setInverted(true);

    latch.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);
    latch.setInverted(true);
    latch.setSensorPhase(true);
    setDefaultCommand(new DoNothing());
  }

  public void setLeftWinch(double speed) {
    leftClimbPrimary.set(speed);
    leftClimbSecondary.set(speed);
  }

  public void setRightWinch(double speed) {
    rightClimbPrimary.set(speed);
    rightClimbSecondary.set(speed);
  }

  public void setWinches(double speed) {
    leftClimbPrimary.set(speed);
    leftClimbSecondary.set(speed);
    rightClimbPrimary.set(speed);
    rightClimbSecondary.set(speed);
  }

  public void resetLatchEncoder() {
    latch.setSelectedSensorPosition(0);
  }

  public void changeClimbBrakeMode(boolean enabled) {
    if (enabled) {
      leftClimbPrimary.setIdleMode(IdleMode.kBrake);
      leftClimbSecondary.setIdleMode(IdleMode.kBrake);
      rightClimbPrimary.setIdleMode(IdleMode.kBrake);
      rightClimbSecondary.setIdleMode(IdleMode.kBrake);
    } else {
      leftClimbPrimary.setIdleMode(IdleMode.kCoast);
      leftClimbSecondary.setIdleMode(IdleMode.kCoast);
      rightClimbPrimary.setIdleMode(IdleMode.kCoast);
      rightClimbSecondary.setIdleMode(IdleMode.kCoast);
    }

  }

}
