/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.PIDDriveInches;
import frc.robot.commands.PIDDriveRotateCustom;
import frc.robot.commands.PIDHatchMechanism;
import frc.robot.commands.TeleopSimulator;
import frc.robot.commands.PIDDriveInchesHoldHatch;

public class LeftRocketLevelTwo extends CommandGroup {
  
  @Override
  protected void initialize() {
    Robot.operatorControl = false;
  }

  public LeftRocketLevelTwo() {
    addSequential(new PIDDriveInchesHoldHatch(81, false));
    addSequential(new PIDDriveRotateCustom(-40));

    // addSequential(new TeleopSimulator());

    // addSequential(new PIDHatchMechanism(935, false));
    // addSequential(new PIDDriveInches(10, true));
    // addSequential(new PIDDriveRotateCustom(-120));
    // addSequential(new TeleopSimulator());
  }

  @Override
  protected void end() {
    Robot.operatorControl = true;
  }
}
