package primitives;

public class Material {
    public Double3 kd =Double3.ZERO;
    public Double3 ks =Double3.ZERO;
    public int Shininess=0;


    /**-----------------Setters---------------------------------*/
    public Material setKd(double kd) {
        this.kd = new Double3(kd);
        return this;
    }

    public Material setKd(Double3 Kd) {
        this.kd = Kd;
        return this;
    }

    public Material setKs(double ks) {
        this.ks = new Double3(ks);
        return this;
    }

    public Material setKs(Double3 Ks) {
        this.ks = Ks;
        return this;
    }

    public Material setShininess(int Shininess) {
        this.Shininess = Shininess;
        return  this;
    }
}