/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.commands.DriveCommand;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * Add your docs here.
 */
public class DriveSubsystem extends Subsystem {
  
  int GEAR_RATIO = 1;
  int TICKS_PER_REV = 4096;

  // Front is the follow motor, and it is based on following the primary motor of its side.
  public CANSparkMax leftDrivePrimary = new CANSparkMax(1, MotorType.kBrushless),
                     leftDriveFront = new CANSparkMax(2, MotorType.kBrushless),
                     rightDriveFront = new CANSparkMax(9, MotorType.kBrushless),
                     rightDrivePrimary = new CANSparkMax(10, MotorType.kBrushless);

  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveCommand());    
    leftDriveFront.follow(leftDrivePrimary);
    rightDriveFront.follow(rightDrivePrimary);
  }

  public void set(double left, double right) {
    leftDrivePrimary.set(left);
    rightDrivePrimary.set(right);
  }

  public double getLeftTicks() {
    return leftDrivePrimary.getEncoder().getPosition();
  }

  public double getRightTicks() {
    return rightDrivePrimary.getEncoder().getPosition();
  }

  public double inchesToTicks(int INCHES) {
    return TICKS_PER_REV*INCHES*GEAR_RATIO;
  }
}
