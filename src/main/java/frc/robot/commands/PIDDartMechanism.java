package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
public class PIDDartMechanism extends Command {
        
    private double driveTicks;
    
    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;
    
    private static final double MAX_PERCENT_VOLTAGE = .5; //regular current draw is ~20 AMPS
    private static final double MIN_PERCENT_VOLTAGE = 0.0;
    
    private final static int allowableCloseLoopError = 1;
       
    private double driveDartP = 1.0;
    
    private double driveDartI = 0;
    
    private double driveDartD = 0;
    
    private double driveDartF = 0;

    public PIDDartMechanism(double ticks) {
        requires(Robot.ACTUATOR_SUBSYSTEM);
    
        this.driveTicks = ticks;
    }
    
        
    protected void initialize() {
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.MotionMagic, 0);
        
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.config_kP(pidIdx, driveDartP, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.config_kI(pidIdx, driveDartI, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.config_kD(pidIdx, driveDartD, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.config_kF(pidIdx, driveDartF, timeoutMs);

        Robot.ACTUATOR_SUBSYSTEM.dartTalon.enableCurrentLimit(true);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configContinuousCurrentLimit(30);

        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
                
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);        

        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configMotionCruiseVelocity(50000, 10);
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.configMotionAcceleration(50000, 10);

        Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.MotionMagic, driveTicks);
    }
    
    protected void execute() {
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
        Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, 0);
    }
    
    protected void interrupted() {
        end();
    }

}
