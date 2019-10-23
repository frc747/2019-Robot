package frc.robot.autonomous;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.PIDDriveRotateCustom;
import frc.robot.commands.PIDDriveInchesHoldHatch;

public class RightRocketLevelTwo extends CommandGroup {

  @Override
  protected void initialize() {
    Robot.autoSideLeft = false;
    Robot.autoSideRight = true;
    Robot.autoSideFaceCargoShip = false;
    Robot.autoFrontFaceCargoShip = false;
    Robot.autoRocket = true;

    Robot.operatorControl = false;
  }

   public RightRocketLevelTwo() {
    addSequential(new PIDDriveInchesHoldHatch(109.775, false), 4);
    // addSequential(new PIDDriveInchesHoldHatch(93, false), 4);
    addSequential(new PIDDriveRotateCustom(45, false), 4);
  }

  @Override
  protected void end() {
    Robot.operatorControl = true;
  }
}
