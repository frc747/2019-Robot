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

/**
 * Add your docs here.
 */
public class IntakeSubsystem extends Subsystem {

  TalonSRX topRoller = new TalonSRX(27);
  TalonSRX bottomRoller = new TalonSRX(28);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setRollers(double top, double bottom) {
    topRoller.set(ControlMode.PercentOutput, top);
    bottomRoller.set(ControlMode.PercentOutput, bottom);
  }

  public void setTop(double top) {
    topRoller.set(ControlMode.PercentOutput, top);
  }

  public void setBottom(double bottom) {
    bottomRoller.set(ControlMode.PercentOutput, bottom);
  }
}
