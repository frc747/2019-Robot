/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class ClimbCommand extends Command {


  double winchMax;
  double crankMax;
  int timeoutMs = 10;
  public ClimbCommand() {
    requires(Robot.climb);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    winchMax = Robot.prefs.getDouble("WINCH", .5);
    crankMax = Robot.prefs.getDouble("CRANK", .5);
    Robot.climb.winch1.configNominalOutputForward(+0, timeoutMs);
    Robot.climb.winch1.configNominalOutputReverse(-0, timeoutMs);
    Robot.climb.winch1.configPeakOutputForward(+winchMax, timeoutMs);
    Robot.climb.winch1.configPeakOutputReverse(-winchMax, timeoutMs);

    
    Robot.climb.winch3.configNominalOutputForward(+0, timeoutMs);
    Robot.climb.winch3.configNominalOutputReverse(-0, timeoutMs);
    Robot.climb.winch3.configPeakOutputForward(+winchMax, timeoutMs);
    Robot.climb.winch3.configPeakOutputReverse(-winchMax, timeoutMs);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    SmartDashboard.putNumber("WINCH MAX: ", winchMax);
    SmartDashboard.putNumber("CRANK MAX: ", crankMax);

    double winchLeftValue = OI.operatorController.getRawAxis(1);
    double winchRightValue = OI.operatorController.getRawAxis(1);
    //double crankValue = OI.operatorController.getRawAxis(5);

    if(Math.abs(winchLeftValue) < .05) {
      winchLeftValue = 0;
    }

    if(Math.abs(winchRightValue) < .05) {
      winchRightValue = 0;
    }

    Robot.climb.setLeftWinch(winchLeftValue);
    Robot.climb.setRightWinch(winchRightValue);
    //Robot.climb.setCrank(crankValue);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
