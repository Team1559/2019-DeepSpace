package org.usfirst.frc.team1559.robot;
import edu.wpi.first.wpilibj.SPI;
import org.usfirst.frc.team1559.robot.subsystems.pixylinevector;
//this class returns the data from the pixy 2
public class Pixy {

    public SPI port;
    private byte[] getFeatures = {(byte)0xae, (byte)0xc1, (byte)0x30, (byte)0x02, (byte)0x00, (byte)0x07, 0, 0};
    private byte[] lampon = {(byte)0xae, (byte)0xc1, (byte)0x16, (byte)0x02, (byte)0x01, (byte)0x01, 0, 0};
    private byte[] lampoff = {(byte)0xae, (byte)0xc1, (byte)0x16, (byte)0x02, (byte)0x00, (byte)0x00, 0, 0};
    public double Ex;
    public double Er;
	public Object v;
    public Pixy() {
        port = new SPI(SPI.Port.kOnboardCS1);
        port.setMSBFirst();
        port.setChipSelectActiveLow();
        port.setClockRate(2000000);
        port.setSampleDataOnTrailingEdge();
        port.setClockActiveLow();
    }

    public byte lampon() {
        byte[] returned = new byte[10];
        port.transaction(lampon, returned, 6);
        return returned[6];
    }

    public byte lampoff() {
        byte[] returned = new byte[10];
        port.transaction(lampoff, returned, 6);
        return returned[6];
    }
    public pixylinevector getvector() {
        byte[] returned = new byte[16];
        port.write(getFeatures, 8);
        port.read(false, returned, 6);
        port.read(false, returned, 16);
         var v=new pixylinevector();
         v.timer++;
         if (returned[2]==49 && returned[7]>0){
            v.x0=returned[8];
            v.y0=returned[9];
            v.x1=returned[10];
            v.y1=returned[11];
            v.index=returned[12];
            v.flags=returned[7];
            v.Ex = v.error_x(v.x0, v.x1);
            Ex = v.Ex;
            v.Er = v.error_r(v.y0, v.y1, v.x0, v.x1);
            Er = v.Er;
            if(v.flags==6) {
                v.status=1;
            }
        }
        return v;
    }
    public double getEx()
        {
            return Ex;
        }
    public double getEr()
        {
            return Er;
        }


        




    
}




