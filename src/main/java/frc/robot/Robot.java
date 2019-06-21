/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HatchSubsystem;
import frc.robot.subsystems.ActuatorSubsystem;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.networktables.*;
import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static boolean climbBrakeMode;
  public static DriveSubsystem DRIVE_SUBSYSTEM = new DriveSubsystem();
  public static HatchSubsystem HATCH_SUBSYSTEM = new HatchSubsystem();
  public static ActuatorSubsystem ACTUATOR_SUBSYSTEM = new ActuatorSubsystem();
  public static ClimbSubsystem climb = new ClimbSubsystem();
  public static OI m_oi;

  public static boolean latchInPos = false;

  public static boolean operatorControl = false;
  public static boolean isAutonomous = false;
  public static boolean isTeleop = false;

  public static boolean autoSideLeft = false;
  public static boolean autoSideRight = false;
  public static boolean autoSideFaceCargoShip = false;
  public static boolean autoFrontFaceCargoShip = false;
  public static boolean autoRocket = false;

  public static String side = "";

  // public static NetworkTable table;
  // public static double x;
  // public static double y;
  // public static double area;
 
	private Command autonomousCommand;
  public Autonomous autonomous;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

  public static final AHRS NAV_X = new AHRS (SPI.Port.kMXP);
    
  public static Preferences prefs;

  public static double getNavXAngle() {
    return NAV_X.getYaw();
    
  }
  
  public static double getNavXAngleRadians() {
    return Math.toRadians(getNavXAngle());
  }
  
  public static void resetNavXAngle() {
    NAV_X.zeroYaw();
    try {
          Thread.sleep(100);
      } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
  }

  @Override
  public void robotInit() {
    prefs = Preferences.getInstance();

    UsbCamera ucamera = CameraServer.getInstance().startAutomaticCapture("cam1", 0);
    ucamera.setResolution(180, 240);

    // ucamera.setResolution(160, 120);
    // ucamera.setFPS(10);
    //might want to lower resolution or fps for the usbcamera to compensate for the limelight
    // ucamera.setResolution(256, 144);
    // ucamera.setFPS(15);
    
    this.autonomous = new Autonomous();

    if(m_oi == null) {
      m_oi = new OI();
    }
    //m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());

  }

  /**e
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    if (!isDisabled()) {
      updateLimelightTracking();
    }
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.Q
   */
  @Override
  public void disabledInit() {
    climb.changeClimbBrakeMode(true);
    DRIVE_SUBSYSTEM.changeDriveBrakeMode(false);
    operatorControl = false;
    isAutonomous = false;
    isTeleop = false;
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    resetNavXAngle();
    climb.changeClimbBrakeMode(true);
    DRIVE_SUBSYSTEM.changeDriveBrakeMode(true);
    // this is now done within the autonomous command groups (within initialize)
    // operatorControl = false;
    isAutonomous = true;
    isTeleop = false;

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
      autonomous.startMode();
      if (autonomousCommand != null) {
          autonomousCommand.start();
      }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    if (DriverStation.getInstance().getMatchTime() < 8.75 && DriverStation.getInstance().getMatchTime() > 0) {
      if (side.compareTo("rightTwo") == 0) {
        OI.table.getEntry("pipeline").setDouble(1.0);
      } else if (side.compareTo("leftTwo") == 0) {
        OI.table.getEntry("pipeline").setDouble(2.0);
      } else if (side.compareTo("left") == 0 || side.compareTo("right") == 0) {
        OI.table.getEntry("pipeline").setDouble(0.0);
      }
    }
    
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    OI.table.getEntry("pipeline").setDouble(0.0);
    resetNavXAngle();
    climb.changeClimbBrakeMode(true);
    DRIVE_SUBSYSTEM.changeDriveBrakeMode(true);
    operatorControl = true;
    isAutonomous = false;
    isTeleop = true;
    
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
  }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  private void updateLimelightTracking() {
    // table = NetworkTableInstance.getDefault().getTable("limelight");
    // x = table.getEntry("tx").getDouble(0);
    // y = table.getEntry("ty").getDouble(0);
    // area = table.getEntry("ta").getDouble(0);

    // x = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    // y = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    // area = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);


  }
}
