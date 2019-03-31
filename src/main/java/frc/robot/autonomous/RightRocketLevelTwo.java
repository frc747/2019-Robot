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
    addSequential(new PIDDriveInchesHoldHatch(80, false));
    addSequential(new PIDDriveRotateCustom(40, false));
  }

  @Override
  protected void end() {
    Robot.operatorControl = true;
  }
}
