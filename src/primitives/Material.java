package primitives;

public class Material {
    public Double3 kd =Double3.ZERO;
    public Double3 ks =Double3.ZERO;
    public Double3 kt =Double3.ZERO; /*transparency*/
    public Double3 kr =Double3.ZERO; /*reflection*/
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

    public Material setKt(Double3 kt) {
        this.kt = kt;
        return this;
    }

    public Material setKt(double ks) {
        this.ks = new Double3(ks);
        return this;
    }

    public Material setKr(Double3 kr) {
        this.kr = kr;
        return this;
    }

    public Material setKr(double ks) {
        this.ks = new Double3(ks);
        return this;
    }
}