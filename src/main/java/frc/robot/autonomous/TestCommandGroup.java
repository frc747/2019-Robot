/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.PIDDriveRotateCustom;
import frc.robot.commands.StepThroughCommand;
import frc.robot.commands.TeleopSimulator;

public class TestCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public TestCommandGroup() {
    addSequential(new TeleopSimulator());
    addSequential(new PIDDriveRotateCustom(90));
    addSequential(new TeleopSimulator());
    addSequential(new PIDDriveRotateCustom(90));
    

  }
}
