package org.firstinspires.ftc.teamcode;

// Importamos las clases para configurar el codigo
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// Importamos el hardware del robot
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

// Importamos las clases de ArcPlanner
import org.firstinspires.ftc.teamcode.ArcPlanner.Hardware.ArcChasis;
import org.firstinspires.ftc.teamcode.ArcPlanner.Hardware.ArcPoint;
import org.firstinspires.ftc.teamcode.ArcPlanner.Core.ArcPose;

@TeleOp
public class FTC_TeleOp extends OpMode
{
    //Creamos las variables
    private DcMotorEx FL, FR, BL, BR;

    private VoltageSensor voltage;

    private ArcChasis chasis;

    private GoBildaPinpointDriver imu;

    private ArcPoint pinpoint;

    @Override
    public void init()
    {
        // Definimos nuestras variables
        FL = hardwareMap.get(DcMotorEx.class, "FL");
        FR = hardwareMap.get(DcMotorEx.class, "FR");
        BL = hardwareMap.get(DcMotorEx.class, "BL");
        BR = hardwareMap.get(DcMotorEx.class, "BR");

        voltage = hardwareMap.voltageSensor.iterator().next();

        chasis = new ArcChasis(FL, FR, BL, BR, voltage);

        imu = hardwareMap.get(GoBildaPinpointDriver.class, "odo");

        pinpoint = new ArcPoint(imu);
    }

    @Override
    public void loop()
    {
        // Creamos las variables de nuestros Joysticks
        float JoystickYL = gamepad1.left_stick_y;
        float JoystickXL = gamepad1.left_stick_x;
        float JoystickXR = gamepad1.right_stick_x;

        // Obtenemos el angulo del robot
        imu.update();
        ArcPose currentPose = pinpoint.getPose();
        double heading = currentPose.heading;

        // Como emplear las funciones de los distintos tipos de manejo
        chasis.FielCentricDrive(JoystickXL, JoystickYL, JoystickXR, heading);
    }
}
