package frc.robot.autonomous;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.PIDDriveRotateCustom;
import frc.robot.commands.PIDDriveInchesHoldHatch;

public class LeftRocketLevelTwo extends CommandGroup {
  
  @Override
  protected void initialize() {
    Robot.autoSideLeft = true;
    Robot.autoSideRight = false;
    Robot.autoSideFaceCargoShip = false;
    Robot.autoFrontFaceCargoShip = false;
    Robot.autoRocket = true;

    Robot.operatorControl = false;
  }

  public LeftRocketLevelTwo() {
    addSequential(new PIDDriveInchesHoldHatch(80, false), 4);
    addSequential(new PIDDriveRotateCustom(-40, false), 4);
  }

  @Override
  protected void end() {
    Robot.operatorControl = true;
  }
}
