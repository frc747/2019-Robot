/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import frc.robot.OI;

public class CargoTrackCommand extends Command {

int timeoutMs = 10;

double speed = 0.65;

double leftValue = 0;
double rightValue = 0;

  public CargoTrackCommand() {
    requires(Robot.DRIVE_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("cargo");
    
    OI.table.getEntry("pipeline").setDouble(1.0);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if(OI.area != 0) {
      leftValue = (speed) + ((.75*(Math.tanh(OI.x/10)))/3);
      rightValue = -((speed) - ((.75*(Math.tanh(OI.x/10)))/3));
    } else {
      leftValue = 0;
      rightValue = 0;
    }
    

    Robot.DRIVE_SUBSYSTEM.set(leftValue, -rightValue);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    OI.table.getEntry("pipeline").setDouble(0.0);
    Robot.DRIVE_SUBSYSTEM.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    OI.table.getEntry("pipeline").setDouble(0.0);
  }
}
