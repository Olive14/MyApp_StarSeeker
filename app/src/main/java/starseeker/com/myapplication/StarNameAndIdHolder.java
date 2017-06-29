package starseeker.com.myapplication;

public enum StarNameAndIdHolder {
    ANDROMEDA("アンドロメダ", 1),
    ANTILA("ポンプ", 2),
    APUS("ふうちょう", 3),
    AQUILA("わし", 4),
    AQUARIUS("みずがめ", 5),
    ARA("さいだん", 6),
    ARIES("おひつじ", 7),
    AURIGA("ぎょしゃ", 8),
    HERDSMAN("うしかい", 9),
    CAELUM("ちょうこくぐ", 10),
    CAMELOPARD("きりん", 11),
    CAPRICORNUS("やぎ", 12),
    CARINA("りゅうこつ", 13),
    CASSIOPEIA("カシオペヤ", 14),
    CENTAURUS("ケンタウルス", 15),
    CEPHEUS("ケフェウス", 16),
    CETUS("くじら", 17),
    CHAMELEON("カメレオン", 18),
    COMPASSES("コンパス", 19),
    CANIS_MAJOR("おおいぬ", 20),
    CANIS_MINOR("こいぬ", 21),
    CRAB("かに", 22),
    COLUMBA("はと", 23),
    COMA_BERENICES("かみのけ", 24),
    CORONA_AUSTRINA("みなみのかんむり", 25),
    CORONA_BOREALIS("かんむり", 26),
    CUP("コップ", 27),
    CRUX("みなみじゅうじ", 28),
    CORVUS("からす", 29),
    CANES_VENATICI("りょうけん", 30),
    CYGNUS("はくちょう", 31),
    DOLPHINS("いるか", 32),
    SWORDFISH("かじき", 33),
    DRACO("りゅう", 34),
    EQUULEUS("こうま", 35),
    ERIDANUS("エリダヌス", 36),
    FORNAX("ろ", 37),
    GEMINI("ふたご", 38),
    GRUS("つる", 39),
    HERCULES("ヘルクレス", 40),
    HOROLOGIUM("とけい", 41),
    HYDRA("うみへび", 42),
    HYDRUS("みずへび", 43),
    INDIAN("インディアン", 44),
    LACETRA("とかげ", 45),
    LEO("しし", 46),
    LEPUS("うさぎ", 47),
    LIBRA("てんびん", 48),
    LEO_MINOR("こじし", 49),
    LUPUS("おおかみ", 50),
    LYNX("やまねこ", 51),
    LYRA("こと", 52),
    MENSA("テーブルさん", 53),
    MICROSCOPIUM("けんびきょう", 54),
    MONOCEROS("いっかくじゅう", 55),
    MUSCA("はえ", 56),
    NORMA("じょうぎ", 57),
    OCTANUS("はちぶんぎ", 58),
    OPHIUCHUS("へびつかい", 59),
    ORION("オリオン", 60),
    PAVO("くじゃく", 61),
    PEGASUS("ペガスス", 62),
    PERSEUS("ペルセウス", 63),
    PHOENIX("ほうおう", 64),
    PICTOR("がか", 65),
    PISCIS_AUSTRINUS("みなみのうお", 66),
    PISCES("うお", 67),
    PUPPIS("とも", 68),
    PYXIS("らしんばん", 69),
    RETICULUM("レチクル", 70),
    SCULPTOR("ちょうこくしつ", 71),
    SCORPIUS("さそり", 72),
    SCUTUM("たて", 73),
    SERPENS("へび", 74),
    SEXTANS("ろくぶんぎ", 75),
    ARROW("や", 76),
    SAGITTARIUS("いて", 77),
    TAURUS("おうし", 78),
    TELESCOPIUM("ぼうえんきょう", 79),
    SOUTHERN_TRIANGLE("みなみのさんかく", 80),
    TRIANGULUM("さんかく", 81),
    TUCANA("きょしちょう", 82),
    URSA_MAJOR("おおぐま", 83),
    URSA_MINOR("こぐま", 84),
    VELA("ほ", 85),
    VIRGO("おとめ", 86),
    VOLANS("とびうお", 87),
    VULPECULA("こぎつね", 88),
    SUN("太陽", 100),
    MOON("月", 101)
    ;

    private String starName;
    private int id;

    private StarNameAndIdHolder(String starName, int id) {
        if(null==starName) {
            throw new NullPointerException();
        }
        this.starName = starName;
        this.id = id;
    }

    String getStarName() {
        return this.starName;
    }

    int getId() {
        return this.id;
    }

    /**
     * Check if the starName is register.
     * If registered, this method return its Enum instance.
     *
     * @param starName star name.
     * @return Enum instance which applies for starName.
     * @throws IllegalArgumentException If the star name is not registered.
     */
    public static StarNameAndIdHolder getStarNameAndIdHolder(String starName) {
        for(StarNameAndIdHolder holder : values()) {
            if(holder.getStarName().equals(starName)) {
                return holder;
            }
        }
        throw new IllegalArgumentException("Not registered. Inputed name: " + starName + "  [StarNameAndIdHolder #getStartNameAndIdHolder]");
    }

}
