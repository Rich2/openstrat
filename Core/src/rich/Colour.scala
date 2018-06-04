/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

sealed trait Colour
{
   def value: Int
   def str: String   
   def canEqual(a: Any) = a.isInstanceOf[Colour]
   override def equals(that: Any): Boolean = that match
   {
      case that: Colour => value == that.value
      case _ => false
   }
   override def hashCode: Int = value.hashCode()
   def red: Int = (value >> 16) & 0xFF // / (256 * 256)
   def green: Int = (value >> 8)  & 0xFF //(value /256) % 256
   def blue: Int = (value >> 0) & 0xFF //(value % 256) % 256
   def alpha: Int = (value >> 24) & 0xFF
   def hexStr = red.hexStr2 - green.hexStr2 - blue.hexStr2 - alpha.hexStr2
   
   def redGl: Float = (red / 256.toFloat)
   def greenGl:Float = (green / 256.toFloat)
   def blueGl: Float = (blue / 256.toFloat)
   def setAlpha(newAlpha: Int): Colour = Colour((value & 0xFFFFFF) | (newAlpha << 24))
   def contrastBW: Colour = ife((red + green + blue) > 128 * 3, Colour.Black, Colour.White)
   def redOrPink: Colour = ife((red + green + blue) > 128 * 3, Colour.DarkRed, Colour.Pink)
   def contrast: Colour =
   {
      def getCol(el: Int): Int = el match
      {
         case el if el < 128 => 255
         case _ => 0
      }
      Colour.fromInts(getCol(red), getCol(green), getCol(blue), alpha)
   }
   def contrast2(other: Colour): Colour =
   {
      def f(i1: Int, i2: Int): Int = 
      {
         val av = (i1 + i2) / 2
         val avd = av.diff(i1).min(av.diff(i2))
         val ld = 0.diff(i1).min(0.diff(i2))
         val hd = 255.diff(i1).min(255.diff(i2))
         Unit match
         {
            case _ if hd > avd && hd > ld => 255
            case _ if avd > ld => av
            case _ => 0
         }
      }
      Colour.fromInts(f(red, other.red), f(green, other.green), f(blue, other.blue), 255)
   }
   
   def darken(factor: Double = 2): Colour =
   {
      def f(primary: Int): Int = (primary / factor).toInt.min(255)
      Colour.fromInts(f(red), f(green), f(blue), alpha)
   }
   def lighten(factor: Double = 2): Colour =
   {
      def f(primary: Int): Int = 256 - ((256 - primary) / factor).toInt.min(255).max(1)
      Colour.fromInts(f(red), f(green), f(blue), alpha)
   }
   def modAlpha(newAlpha: Int) = Colour.fromInts(red, green, blue, newAlpha)
   //def glCommaed(alpha: Double = 1.0): String = Seq(redGl, greenGl, blueGl, alpha.toString).commaParenth
   //def glVec4(alpha: Double = 1.0): String = "vec4" - glCommaed(alpha)
}

trait ColourNamed extends Colour

trait WithColour extends AnyRef
{
   def colour: Colour
   def colourContrast: Colour = colour.contrast
   def colourContrast2(other: Colour): Colour = colour.contrast2(other)
}

object Colour
{
   def apply(valueIn: Int): Colour = new Colour
   {
      override val value: Int = valueIn
      def str: String = "#" - hexStr
     // override def persistStr = "0x" - hexStr
   }
   def fromInts(red: Int, green: Int, blue: Int, a: Int = 255): Colour = Colour(a * 256 * 256 * 256 + red * 256 * 256 + green * 256 + blue)
      
   object AntiqueWhite extends ColourNamed { val str: String = "AntiqueWhite"; val value = 0xFFFAEBD7 }
   object Aqua extends ColourNamed { val str: String = "Aqua"; val value = 0xFF00FFFF }
   object Aquamarine extends ColourNamed { val str: String = "Aquamarine"; val value = 0xFF7FFFD4 }
   object Azure extends ColourNamed { val str: String = "Azure"; val value = 0xFFF0FFFF }
   object Beige extends ColourNamed { val str: String = "Beige"; val value = 0xFFF5F5DC }
   object Bisque extends ColourNamed { val str: String = "Bisque"; val value = 0xFFFFE4C4 }
   object Black extends ColourNamed { val str: String = "Black"; val value = 0xFF000000 }
   object BlanchedAlmond extends ColourNamed { val str: String = "BlanchedAlmond"; val value = 0xFFFFEBCD }
   object Blue extends ColourNamed { val str: String = "Blue"; val value = 0xFF0000FF }
   object BlueViolet extends ColourNamed { val str: String = "BlueViolet"; val value = 0xFF8A2BE2 }
   object BrightSkyBlue extends ColourNamed { val str: String = "BrightSkyBlue"; val value = 0xFFF0F9FF }
   object Brown extends ColourNamed { val str: String = "Brown"; val value = 0xFFA52A2A }
   object BurlyWood extends ColourNamed { val str: String = "BurlyWood"; val value = 0xFFDEB887 }
   object CadetBlue extends ColourNamed { val str: String = "CadetBlue"; val value = 0xFF5F9EA0 }
   object Chartreuse extends ColourNamed { val str: String = "Chartreuse"; val value = 0xFF7FFF00 }
   object Chocolate extends ColourNamed { val str: String = "Chocolate"; val value = 0xFFD2691E }
   object Coral extends ColourNamed { val str: String = "Coral"; val value = 0xFFFF7F50 }
   object CornflowerBlue extends ColourNamed { val str: String = "CornflowerBlue"; val value = 0xFF6495ED }
   object Cornsilk extends ColourNamed { val str: String = "Cornsilk"; val value = 0xFFFFF8DC }
   object Crimson extends ColourNamed { val str: String = "Crimson"; val value = 0xFFDC143C }
   object Cyan extends ColourNamed { val str: String = "Cyan"; val value = 0xFF00FFFF }
   object DarkBlue extends ColourNamed { val str: String = "DarkBlue"; val value = 0xFF00008B }
   object DarkCyan extends ColourNamed { val str: String = "DarkCyan"; val value = 0xFF008B8B }
   object DarkGoldenRod extends ColourNamed { val str: String = "DarkGoldenRod"; val value = 0xFFB8860B }
   object DarkGray extends ColourNamed { val str: String = "DarkGray"; val value = 0xFFA9A9A9 }
   object DarkGreen extends ColourNamed { val str: String = "DarkGreen"; val value = 0xFF006400 }
   object DarkKhaki extends ColourNamed { val str: String = "DarkKhaki"; val value = 0xFFBDB76B }
   object DarkMagenta extends ColourNamed { val str: String = "DarkMagenta"; val value = 0xFF8B008B }
   object DarkOliveGreen extends ColourNamed { val str: String = "DarkOliveGreen"; val value = 0xFF556B2F }
   object DarkOrange extends ColourNamed { val str: String = "DarkOrange"; val value = 0xFFFF8C00 }
   object DarkOrchid extends ColourNamed { val str: String = "DarkOrchid"; val value = 0xFF9932CC }
   object DarkRed extends ColourNamed { val str: String = "DarkRed"; val value = 0xFF8B0000 }
   object DarkSalmon extends ColourNamed { val str: String = "DarkSalmon"; val value = 0xFFE9967A }
   object DarkSeaGreen extends ColourNamed { val str: String = "DarkSeaGreen"; val value = 0xFF8FBC8F }
   object DarkSlateBlue extends ColourNamed { val str: String = "DarkSlateBlue"; val value = 0xFF483D8B }
   object DarkSlateGray extends ColourNamed { val str: String = "DarkSlateGray"; val value = 0xFF2F4F4F }
   object DarkTurquoise extends ColourNamed { val str: String = "DarkTurquoise"; val value = 0xFF00CED1 }
   object DarkViolet extends ColourNamed { val str: String = "DarkViolet"; val value = 0xFF9400D3 }
   object DarkYellow extends ColourNamed { val str: String = "DarkYellow"; val value = 0xFFEEEE00 }
   object DeepPink extends ColourNamed { val str: String = "DeepPink"; val value = 0xFFFF1493 }
   object DeepSkyBlue extends ColourNamed { val str: String = "DeepSkyBlue"; val value = 0xFF00BFFF }
   object DimGray extends ColourNamed { val str: String = "DimGray"; val value = 0xFF696969 }
   object DodgerBlue extends ColourNamed { val str: String = "DodgerBlue"; val value = 0xFF1E90FF }
   object FireBrick extends ColourNamed { val str: String = "FireBrick"; val value = 0xFFB22222 }
   object FloralWhite extends ColourNamed { val str: String = "FloralWhite"; val value = 0xFFFFFAF0 }
   object ForestGreen extends ColourNamed { val str: String = "ForestGreen"; val value = 0xFF228B22 }
   object Fuchsia extends ColourNamed { val str: String = "Fuchsia"; val value = 0xFFFF00F0 }
   object Gainsboro extends ColourNamed { val str: String = "Gainsboro"; val value = 0xFFDCDCDC }
   object GhostWhite extends ColourNamed { val str: String = "GhostWhite"; val value = 0xFFF8F8FF }
   object Gold extends ColourNamed { val str: String = "Gold"; val value = 0xFFFFD700 }
   object GoldenRod extends ColourNamed { val str: String = "GoldenRod"; val value = 0xFFDAA520 }
   object Gray extends ColourNamed { val str: String = "Gray"; val value = 0xFF808080 }
   object Green extends ColourNamed { val str: String = "Green"; val value = 0xFF008000 }
   object GreenYellow extends ColourNamed { val str: String = "GreenYellow"; val value = 0xFFADFF2F }
   object HoneyDew extends ColourNamed { val str: String = "HoneyDew"; val value = 0xFFF0FFF0 }
   object HotPink extends ColourNamed { val str: String = "HotPink"; val value = 0xFFFF69B4 }
   object IndianRed extends ColourNamed { val str: String = "IndianRed"; val value = 0xFFCD5C5C }
   object Indigo extends ColourNamed { val str: String = "Indigo"; val value = 0xFF4B0082 }
   object Ivory extends ColourNamed { val str: String = "Ivory"; val value = 0xFFFFFFF0 }
   object Khaki extends ColourNamed { val str: String = "Khaki"; val value = 0xFFF0E68C }
   object Lavender extends ColourNamed { val str: String = "Lavender"; val value = 0xFFE6E6FA }
   object LavenderBlush extends ColourNamed { val str: String = "LavenderBlush"; val value = 0xFFFFF0F5 }
   object LawnGreen extends ColourNamed { val str: String = "LawnGreen"; val value = 0xFF7CFC00 }
   object LemonChiffon extends ColourNamed { val str: String = "LemonChiffon"; val value = 0xFFFFFACD }
   object LemonLime extends ColourNamed { val str: String = "LemonLime"; val value = 0xFFBACD22 }
   object LightBlue extends ColourNamed { val str: String = "LightBlue"; val value = 0xFFADD8E6 }
   object LightCoral extends ColourNamed { val str: String = "LightCoral"; val value = 0xFFF08080 }
   object LightCyan extends ColourNamed { val str: String = "LightCyan"; val value = 0xFFE0FFFF }
   object LightGoldenRodYellow extends ColourNamed { val str: String = "LightGoldenRodYellow"; val value = 0xFFFAFAD2 }
   object LightGreen extends ColourNamed { val str: String = "LightGreen"; val value = 0xFF90EE90 }
   object LightGrey extends ColourNamed { val str: String = "LightGrey"; val value = 0xFFD3D3D3 }
   object LightPink extends ColourNamed { val str: String = "LightPink"; val value = 0xFFFFB6C1 }
   object LightRed extends ColourNamed { val str: String = "LightRed"; val value = 0xFFFF7755 }
   object LightSalmon extends ColourNamed { val str: String = "LightSalmon"; val value = 0xFFFFA07A }
   object LightSeaGreen extends ColourNamed { val str: String = "LightSeaGreen"; val value = 0xFF20B2AA }
   object LightSkyBlue extends ColourNamed { val str: String = "LightSkyBlue"; val value = 0xFF87CEFA }
   object LightSlateGray extends ColourNamed { val str: String = "LightSlateGray"; val value = 0xFF778899 }
   object LightSteelBlue extends ColourNamed { val str: String = "LightSteelBlue"; val value = 0xFFB0C4DE }
   object LightYellow extends ColourNamed { val str: String = "LightYellow"; val value = 0xFFFFFFE0 }
   object Lime extends ColourNamed { val str: String = "Lime"; val value = 0xFF00FF00 }
   object LimeGreen extends ColourNamed { val str: String = "LimeGreen"; val value = 0xFF32CD32 }
   object Linen extends ColourNamed { val str: String = "Linen"; val value = 0xFFFAF0E6 }
   object Magenta extends ColourNamed { val str: String = "Magenta"; val value = 0xFFFF00FF }
   object Maroon extends ColourNamed { val str: String = "Maroon"; val value = 0xFF800000 }
   object MediumAquaMarine extends ColourNamed { val str: String = "MediumAquaMarine"; val value = 0xFF66CDAA }
   object MediumBlue extends ColourNamed { val str: String = "MediumBlue"; val value = 0xFF0000CD }
   object MediumOrchid extends ColourNamed { val str: String = "MediumOrchid"; val value = 0xFFBA55D3 }
   object MediumPurple extends ColourNamed { val str: String = "MediumPurple"; val value = 0xFF9370DB }
   object MediumSeaGreen extends ColourNamed { val str: String = "MediumSeaGreen"; val value = 0xFF3CB371 }
   object MediumSlateBlue extends ColourNamed { val str: String = "MediumSlateBlue"; val value = 0xFF7B68EE }
   object MediumSpringGreen extends ColourNamed { val str: String = "MediumSpringGreen"; val value = 0xFF00FA9A }
   object MediumTurquoise extends ColourNamed { val str: String = "MediumTurquoise"; val value = 0xFF48D1CC }
   object MediumVioletRed extends ColourNamed { val str: String = "MediumVioletRed"; val value = 0xFFC71585 }
   object MidnightBlue extends ColourNamed { val str: String = "MidnightBlue"; val value = 0xFF191970 }
   object MintCream extends ColourNamed { val str: String = "MintCream"; val value = 0xFFF5FFFA }
   object MistyRose extends ColourNamed { val str: String = "MistyRose"; val value = 0xFFFFE4E1 }
   object Moccasin extends ColourNamed { val str: String = "Moccasin"; val value = 0xFFFFE4B5 }
   object NavajoWhite extends ColourNamed { val str: String = "NavajoWhite"; val value = 0xFFFFDEAD }
   object Navy extends ColourNamed { val str: String = "Navy"; val value = 0xFF000080 }
   object OldLace extends ColourNamed { val str: String = "OldLace"; val value = 0xFFFDF5E6 }
   object Olive extends ColourNamed { val str: String = "Olive"; val value = 0xFF808000 }
   object OliveDrab extends ColourNamed { val str: String = "OliveDrab"; val value = 0xFF6B8E23 }
   object Orange extends ColourNamed { val str: String = "Orange"; val value = 0xFFFFA500 }
   object OrangeRed extends ColourNamed { val str: String = "OrangeRed"; val value = 0xFFFF4500 }
   object Orchid extends ColourNamed { val str: String = "Orchid"; val value = 0xFFDA70D6 }
   object PaleGoldenRod extends ColourNamed { val str: String = "PaleGoldenRod"; val value = 0xFFEEE8AA }
   object PaleGreen extends ColourNamed { val str: String = "PaleGreen"; val value = 0xFF98FB98 }
   object PaleTurquoise extends ColourNamed { val str: String = "PaleTurquoise"; val value = 0xFFAFEEEE }
   object PaleVioletRed extends ColourNamed { val str: String = "PaleVioletRed"; val value = 0xFFDB7093 }
   object PapayaWhip extends ColourNamed { val str: String = "PapayaWhip"; val value = 0xFFFFEFD5 }
   object PeachPuff extends ColourNamed { val str: String = "PeachPuff"; val value = 0xFFFFDAB9 }
   object Peru extends ColourNamed { val str: String = "Peru"; val value = 0xFFCD853F }
   object Pink extends ColourNamed { val str: String = "Pink"; val value = 0xFFFFC0CB }
   object Plum extends ColourNamed { val str: String = "Plum"; val value = 0xFFDDA0DD }
   object PowderBlue extends ColourNamed { val str: String = "PowderBlue"; val value = 0xFFB0E0E6 }
   object Purple extends ColourNamed { val str: String = "Purple"; val value = 0xFF800080 }
   object Red extends ColourNamed { val str: String = "Red"; val value = 0xFFFF0000 }
   object RosyBrown extends ColourNamed { val str: String = "RosyBrown"; val value = 0xFFBC8F8F }
   object RoyalBlue extends ColourNamed { val str: String = "RoyalBlue"; val value = 0xFF4169E1 }
   object SaddleBrown extends ColourNamed { val str: String = "SaddleBrown"; val value = 0xFF8B4513 }
   object Salmon extends ColourNamed { val str: String = "Salmon"; val value = 0xFFFA8072 }
   object SandyBrown extends ColourNamed { val str: String = "SandyBrown"; val value = 0xFFF4A460 }
   object SeaGreen extends ColourNamed { val str: String = "SeaGreen"; val value = 0xFF2E8B57 }
   object SeaShell extends ColourNamed { val str: String = "SeaShell"; val value = 0xFFFFF5EE }
   object Sienna extends ColourNamed { val str: String = "Sienna"; val value = 0xFFA0522D }
   object Silver extends ColourNamed { val str: String = "Silver"; val value = 0xFFC0C0C0 }
   object SkyBlue extends ColourNamed { val str: String = "SkyBlue"; val value = 0xFF87CEEB }
   object SlateBlue extends ColourNamed { val str: String = "SlateBlue"; val value = 0xFF6A5ACD }
   object SlateGray extends ColourNamed { val str: String = "SlateGray"; val value = 0xFF708090 }
   object Snow extends ColourNamed { val str: String = "Snow"; val value = 0xFFFFFAFA }
   object SpringGreen extends ColourNamed { val str: String = "SpringGreen"; val value = 0xFF00FF7F }
   object SteelBlue extends ColourNamed { val str: String = "SteelBlue"; val value = 0xFF4682B4 }
   object Tan extends ColourNamed { val str: String = "Tan"; val value = 0xFFD2B48C }
   object Teal extends ColourNamed { val str: String = "Teal"; val value = 0xFF008080 }
   object Thistle extends ColourNamed { val str: String = "Thistle"; val value = 0xFFD8BFD8 }
   object Tomato extends ColourNamed { val str: String = "Tomato"; val value = 0xFFFF6347 }
   object Turquoise extends ColourNamed { val str: String = "Turquoise"; val value = 0xFF40E0D0 }
   object Violet extends ColourNamed { val str: String = "Violet"; val value = 0xFFEE82EE }
   object Wheat extends ColourNamed { val str: String = "Wheat"; val value = 0xFFF5DEB3 }
   object White extends ColourNamed { val str: String = "White"; val value = 0xFFFFFFFF }
   object WhiteSmoke extends ColourNamed { val str: String = "WhiteSmoke"; val value = 0xFFF5F5F5 }
   object Yellow extends ColourNamed { val str: String = "Yellow"; val value = 0xFFFFFF00 }
   object YellowGreen extends ColourNamed { val str: String = "YellowGreen"; val value = 0xFF9ACD32 }
  
   
   val colourConstants: Map[String, Colour] = Map(
("AntiqueWhite", AntiqueWhite), ("Aqua", Aqua), ("Aquamarine", Aquamarine), ("Azure", Azure), ("Beige", Beige),
("Bisque", Bisque), ("Black", Black), ("BlanchedAlmond", BlanchedAlmond), ("Blue", Blue), ("BlueViolet", BlueViolet),
("BrightSkyBlue", BrightSkyBlue), ("Brown", Brown), ("BurlyWood", BurlyWood), ("CadetBlue", CadetBlue), ("Chartreuse", Chartreuse),
("Chocolate", Chocolate), ("Coral", Coral), ("CornflowerBlue", CornflowerBlue), ("Cornsilk", Cornsilk), ("Crimson", Crimson),
("Cyan", Cyan), ("DarkBlue", DarkBlue), ("DarkCyan", DarkCyan), ("DarkGoldenRod", DarkGoldenRod),
("DarkGray", DarkGray), ("DarkGreen", DarkGreen), ("DarkKhaki", DarkKhaki), ("DarkMagenta", DarkMagenta),
("DarkOliveGreen", DarkOliveGreen), ("DarkOrange", DarkOrange), ("DarkOrchid", DarkOrchid), ("DarkRed", DarkRed),
("DarkSalmon", DarkSalmon), ("DarkSeaGreen", DarkSeaGreen), ("DarkSlateBlue", DarkSlateBlue), ("DarkSlateGray", DarkSlateGray),
("DarkTurquoise", DarkTurquoise), ("DarkViolet", DarkViolet), ("DarkYellow", DarkYellow), ("DeepPink", DeepPink),
("DeepSkyBlue", DeepSkyBlue), ("DimGray", DimGray), ("DodgerBlue", DodgerBlue), ("FireBrick", FireBrick),
("FloralWhite", FloralWhite), ("ForestGreen", ForestGreen), ("Fuchsia", Fuchsia), ("Gainsboro", Gainsboro),
("GhostWhite", GhostWhite), ("Gold", Gold), ("GoldenRod", GoldenRod), ("Gray", Gray), ("Green", Green),
("GreenYellow", GreenYellow), ("HoneyDew", HoneyDew), ("HotPink", HotPink), ("IndianRed", IndianRed), ("Indigo", Indigo),
("Ivory", Ivory), ("Khaki", Khaki), ("Lavender", Lavender), ("LavenderBlush", LavenderBlush),
("LawnGreen", LawnGreen), ("LemonChiffon", LemonChiffon), ("LemonLime", LemonLime), ("LightBlue", LightBlue),
("LightCoral", LightCoral), ("LightCyan", LightCyan), ("LightGoldenRodYellow", LightGoldenRodYellow),
("LightGreen", LightGreen), ("LightGrey", LightGrey), ("LightPink", LightPink), ("LightRed", LightRed),
("LightSalmon", LightSalmon), ("LightSeaGreen", LightSeaGreen), ("LightSkyBlue", LightSkyBlue),
("LightSlateGray", LightSlateGray), ("LightSteelBlue", LightSteelBlue), ("LightYellow", LightYellow), ("Lime", Lime),
("LimeGreen", LimeGreen), ("Linen", Linen), ("Magenta", Magenta), ("Maroon", Maroon), ("MediumAquaMarine", MediumAquaMarine),
("MediumBlue", MediumBlue), ("MediumOrchid", MediumOrchid), ("MediumPurple", MediumPurple), ("MediumSeaGreen", MediumSeaGreen),
("MediumSlateBlue", MediumSlateBlue), ("MediumSpringGreen", MediumSpringGreen), ("MediumTurquoise", MediumTurquoise),
("MediumVioletRed", MediumVioletRed), ("MidnightBlue", MidnightBlue), ("MintCream", MintCream), ("MistyRose", MistyRose),
("Moccasin", Moccasin), ("NavajoWhite", NavajoWhite), ("Navy", Navy), ("OldLace", OldLace), ("Olive", Olive),
("OliveDrab", OliveDrab), ("Orange", Orange), ("OrangeRed", OrangeRed), ("Orchid", Orchid), ("PaleGoldenRod", PaleGoldenRod),
("PaleGreen", PaleGreen), ("PaleTurquoise", PaleTurquoise), ("PaleVioletRed", PaleVioletRed), ("PapayaWhip", PapayaWhip),
("PeachPuff", PeachPuff), ("Peru", Peru), ("Pink", Pink), ("Plum", Plum), ("PowderBlue", PowderBlue), ("Purple", Purple),
("Red", Red), ("RosyBrown", RosyBrown), ("RoyalBlue", RoyalBlue), ("SaddleBrown", SaddleBrown),
("Salmon", Salmon), ("SandyBrown", SandyBrown), ("SeaGreen", SeaGreen), ("SeaShell", SeaShell), ("Sienna", Sienna),
("Silver", Silver), ("SkyBlue", SkyBlue), ("SlateBlue", SlateBlue), ("SlateGray", SlateGray), ("Snow", Snow),
("SpringGreen", SpringGreen), ("SteelBlue", SteelBlue), ("Tan", Tan), ("Teal", Teal), ("Thistle", Thistle), ("Tomato", Tomato),
("Turquoise", Turquoise), ("Violet", Violet), ("Wheat", Wheat), ("White", White), ("WhiteSmoke", WhiteSmoke),
("Yellow", Yellow), ("YellowGreen", YellowGreen)    
   )
   
}