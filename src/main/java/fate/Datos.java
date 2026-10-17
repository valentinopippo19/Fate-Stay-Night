package fate;

/**
 * Base procedural del dominio.
 * Se mantienen arreglos paralelos para que los algoritmos de Programación III
 * trabajen sobre índices y no sobre un modelo POO de entidades.
 */
public final class Datos {
  private Datos() {}

  public static final String[] NOMBRE={"Saber","Rin Tohsaka","Sakura Matou","Rider","Illyasviel","Saber Alter","Jeanne d'Arc","Scathach","Mash Kyrielight","Nero Claudius","Ereshkigal","Atalanta"};
  public static final String[] IDENTIDAD={"Artoria Pendragon","Rin Tohsaka","Sakura Matou","Medusa","Illyasviel von Einzbern","Artoria Alter","Jeanne d'Arc","Scathach","Mash Kyrielight","Nero Claudius","Ereshkigal","Atalanta"};
  public static final String[] CLASE={"Saber","Caster","Sakura","Rider","Berserker","Saber","Ruler","Lancer","Shielder","Saber","Lancer","Archer"};
  public static final int[] PODER={960,820,790,850,870,990,900,940,780,860,930,875};
  public static final int[] COSTO={9,7,6,7,8,10,9,10,5,7,9,8};
  public static final int[] MANA={88,95,98,70,80,58,90,92,84,86,96,82};
  public static final int[] VIDA={1500,1250,1320,1400,1450,1750,1500,1600,1280,1380,1580,1420};
  public static final String[] DEFENSA={"A","B+","B","B","A","A+","A","A","A+","B","A","B+"};
  public static final String[] MASTER={"Shirou Emiya","Rin Tohsaka","Shinji Matou","Shirou Emiya","Illya","Shirou Emiya","Ritsuka Fujimaru","Ritsuka Fujimaru","Ritsuka Fujimaru","Saber","Ritsuka Fujimaru","Ritsuka Fujimaru"};
  public static final String[] ARMA={"Excalibur","Gemas de alto rango","Magia de los Matou","Mystic Eyes / Bellerophon","Bastón y hechicería","Excalibur Morgan","Estandarte de Orleans","Gae Bolg / Runas","Lord Chaldeas","Aestus Estus","Key of the Underworld / Noble Phantasm","Arco de Tauropolos"};
  public static final String[] NOBLE={"Excalibur","Gematria / Magecraft","Imaginary Numbers","Bellerophon","Ilya's Castle","Excalibur Morgan","Luminosité Eternelle","Gáe Bolg Alternative","Lord Chaldeas","Aestus Domus Aurea","Kur Kigal Irkalla","Phoebus Catastrophe"};
  public static final String[] HABILIDADES={
    "Instinto · Mana Burst · Avalon",
    "Gemas · Gandr · Runas · Reinforcement",
    "Imaginary Numbers · Absorción · Alteración",
    "Mystic Eyes · Blood Fort Andromeda · Bellerophon",
    "Hechicería Einzbern · Familiar · Territory",
    "Mana Burst (Black) · Magic Resistance · Instinto",
    "True Name Discernment · Revelation · Magic Resistance",
    "Primordial Rune · Wisdom of Dún Scáith · God Slayer",
    "Obscurant · Shield · Defensive Formation",
    "Imperial Privilege · Migraine · Golden Rule",
    "Goddess Authority · Mana Burst · Underworld Authority",
    "Independent Action · Clairvoyance (Tauropolos) · Beyond the Aegean"
  };
  public static final String[] HISTORIA={
    "La legendaria Rey de los Caballeros. Fue convocada como Saber y lucha para alcanzar un milagro capaz de corregir el destino de Camelot. Su código de honor y voluntad son el núcleo de su leyenda.",
    "Maga de la familia Tohsaka y participante de la Quinta Guerra del Santo Grial. Es experta en gemas, runas y estrategia; combina talento mágico con una personalidad competitiva.",
    "Heredera de una familia de magos y recipiente de una enorme energía mágica. Su historia está marcada por los Matou, el conflicto del Grial y la lucha por recuperar su propia voluntad.",
    "Medusa, figura de la mitología griega, invocada como Rider. Sus Mystic Eyes y Bellerophon le permiten dominar el campo de batalla mediante velocidad y control.",
    "La pequeña heredera de los Einzbern y Master de Berserker. Tras una apariencia infantil posee un conocimiento mágico excepcional y una relación profunda con el ritual del Grial.",
    "Una versión corrompida de Artoria. Su mana negro incrementa brutalmente su fuerza y transforma Excalibur en Excalibur Morgan, convirtiéndola en una combatiente de enorme poder.",
    "La doncella de Orleans y Santa guerrera. Su clase Ruler representa equilibrio, protección y autoridad espiritual; su estandarte es símbolo de resistencia y liderazgo.",
    "Guerrera legendaria de Escocia y maestra de héroes. Domina las lanzas y las runas, y su experiencia convierte cualquier enfrentamiento en una prueba táctica.",
    "Demiservant de Chaldea y escudo viviente. Su defensa, determinación y capacidad de proteger aliados la convierten en pieza clave para formar equipos resistentes.",
    "Emperatriz romana convertida en Servant. Ama el arte, el escenario y el combate; su Noble Phantasm convierte el campo de batalla en un teatro imperial.",
    "Diosa sumeria del inframundo y poderosa Servant Lancer. Ereshkigal gobierna Kur con una mezcla de autoridad divina, compasión y una determinación feroz para proteger a quienes considera bajo su cuidado.",
    "Heroína de la mitología griega convertida en Archer. Atalanta es una cazadora excepcional, veloz y precisa, cuyo orgullo y sentido de protección hacia los niños guían sus decisiones en combate."
  };
  public static final String[] COLOR={"#e7d9ff","#e9a8ff","#8ec5ff","#d9b2ff","#f5c2e7","#9a7bff","#fff1b8","#a8e6ff","#b8d7ff","#ffb3d9","#c6a0ff","#d9a7ff"};
  public static final String[] IMAGEN={
    "/Saber Artoria.jpg","/Rin Tohsaka.jpg","/Sakura Matou.jpg","/Rider Medusa.jpg","/Illyasviel von Einzbern.jpg","/Saber Alter.jpg","/Jeanne d'Arc.jpg","/Scathach.jpg","/Mash Kyrielight.jpg","/Nero Claudius.jpg","/Ereshkigal.jpg","/Atalanta.jpg"
  };
  // Videos embebidos de YouTube. No se descargan ni se cargan desde el proyecto.
  public static final String[] VIDEO={
    "gHooJLcpmY8",        // Saber - trailer oficial UBW
    "xSTbJDdWbyQ",        // Rin Tohsaka - Aniplex USA
    "Fqq7oaOYr28",        // Sakura / Heaven's Feel - Aniplex
    "gHooJLcpmY8",        // Rider - trailer Fate/stay night como material audiovisual
    "SYMqrb4_mcU",        // Illyasviel - trailer oficial UBW
    "gHooJLcpmY8",        // Saber Alter - material Fate/stay night
    "PVsNKUNU9X8",        // Jeanne - material Fate/Grand Order / Fate
    "Kqr24DO_ywI",        // Scathach - trailer oficial Fate/EXTELLA LINK
    "U-GP2qKBGnw",        // Mash - material oficial Fate/Grand Order
    "kDg8Z_OaAgw",        // Nero - material Fate oficial
    "e_hMkEgdB_U",        // Ereshkigal - Aniplex USA
    "gHooJLcpmY8"         // Atalanta - material audiovisual Fate
  };
  public static final int N=NOMBRE.length;
}
