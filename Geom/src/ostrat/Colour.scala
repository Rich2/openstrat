/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import geom._, pWeb._, collection.mutable.ArrayBuffer, pParse._

/** The argbValue must start with 0xFF if the default full opacity is required. So 0xFFFF0000 gives full opacity Red */
class Colour(val argbValue: Int) extends AnyVal with FillFacet with ElemInt1
{ 
  override def toString: String = Colour.persistImplicit.strT(this)

  /** The fill attribute for SVG. */
  def fillAttrib: FillAttrib = FillAttrib(this)
  
  override def attribs: RArr[XmlAtt] = RArr(fillAttrib)
  @inline final override def intValue: Int = argbValue 
  def webStr: String = "#" + rgbHexStr + alpha.hexStr2
  def svgStr: String = Colour.valueToStr.get(this).fold(hexStr)(_.toLowerCase)
  def canEqual(a: Any) = a.isInstanceOf[Colour]
  def alpha: Int = (argbValue >> 24) & 0xFF
  def red: Int = (argbValue >> 16) & 0xFF // / (256 * 256)
  def green: Int = (argbValue >> 8)  & 0xFF //(argbValue /256) % 256
  def blue: Int = (argbValue >> 0) & 0xFF //(argbValue % 256) % 256

  def rgbHexStr = red.hexStr2 + green.hexStr2 + blue.hexStr2
  def hexStr = "0x" + alpha.hexStr2 + rgbHexStr
  def redGl: Float = (red / 256.toFloat)
  def greenGl:Float = (green / 256.toFloat)
  def blueGl: Float = (blue / 256.toFloat)
  def setAlpha(newAlpha: Int): Colour = Colour((argbValue & 0xFFFFFF) | (newAlpha << 24))
  def contrastBW: Colour = ife((red + green + blue) > 128 * 3, Colour.Black, Colour.White)
  def redOrPink: Colour = ife((red + green + blue) > 128 * 3, Colour.DarkRed, Colour.Pink)

  def nextFrom(seq: Colours): Colour = seq.findIndex(this) match
  { case NoInt => seq(0)
    case SomeInt(i) if i >= seq.length - 1 => seq(0)
    case SomeInt(i) => seq(i + 1)
  }

  def nextFromRainbow: Colour = nextFrom(Colours.rainbow)
  
  /** Returns the colour with the greatest contrast */
  def contrast: Colour =
  {
    def getCol(el: Int): Int = el match
    { case el if el < 128 => 255
      case _ => 0
    }
    Colour.fromInts(getCol(red), getCol(green), getCol(blue), alpha)
  }

/** Returns the colour that most contrasts with the 2 colours. This is useful for text that is displayed across 2 background colours. */  
def contrast2(other: Colour): Colour =
  {
    def f(i1: Int, i2: Int): Int = 
    { val av = (i1 + i2) / 2
      val avd = av.diff(i1).min(av.diff(i2))
      val ld = 0.diff(i1).min(0.diff(i2))
      val hd = 255.diff(i1).min(255.diff(i2))
      ife2(
        hd > avd && hd > ld, 255,
        avd > ld, av,
        0)
    }
    Colour.fromInts(f(red, other.red), f(green, other.green), f(blue, other.blue), 255)
  }
  
  /** Darkens a colour by a defualt value of 2. */
  def darken(factor: Double = 2): Colour =
  { def f(primary: Int): Int = (primary / factor).toInt.min(255)
    Colour.fromInts(f(red), f(green), f(blue), alpha)
  }

  /** Lightens a colour by a default value of 2 */
  def lighten(factor: Double = 2): Colour =
  { def f(primary: Int): Int = 256 - ((256 - primary) / factor).toInt.min(255).max(1)
    Colour.fromInts(f(red), f(green), f(blue), alpha)
  }

  /** Modifies the alpha value of the rgba Int */
  def modAlpha(newAlpha: Int) = Colour.fromInts(red, green, blue, newAlpha)
  //def glCommaed(alpha: Double = 1.0): String = Seq(redGl, greenGl, blueGl, alpha.toString).commaParenth
  //def glVec4(alpha: Double = 1.0): String = "vec4" - glCommaed(alpha)
  def hasName: Boolean = Colour.valueToStr.contains(this)
}

/** This trait provides a few handy methods for classes with the colour member */
trait Coloured extends AnyRef
{ def colour: Colour
  def contrast: Colour = colour.contrast
  def colourContrast2(other: Colour): Colour = colour.contrast2(other)
  def contrastBW = colour.contrastBW
}

/** Companion object for Colour class, contains named values for the standard web colours and implicit instances for various type classes. */
object Colour
{
  implicit val eqImplicit: EqT[Colour] = (c1, c2) => c1.argbValue == c2.argbValue

  implicit val persistImplicit: Persist[Colour] = new PersistSimple[Colour]("Colour")
  {
    def fromExpr(expr: Expr): EMon[Colour] = expr match
    { case IdentLowerToken(_, typeName) if Colour.strToValue.contains(typeName) => Good(Colour.strToValue(typeName))
      case Nat0xToken(_, _) => ??? //Good(Colour(v.toInt))
      case AlphaBracketExpr(IdentUpperToken(_, "Colour"), Arr1(BracketedStatements(Arr1(st), Parenthesis, _, _))) => st.expr match
      { case Nat0xToken(_, v) => ??? //Good(Colour(v.toInt))
        case _ => expr.exprParseErr[Colour](this)
      }
      case _ => expr.exprParseErr[Colour](this)
    }
    def strT(obj: Colour): String = Colour.valueToStr.get(obj).fold(obj.hexStr)(c => c)
  }

  implicit val arrBuildImplicit: ArrMapBuilder[Colour, Colours] = new Int1ArrMapBuilder[Colour, Colours]
  { type BuffT = ColourBuff
    override def fromIntArray(array: Array[Int]): Colours = new Colours(array)

    override def fromIntBuffer(buffer: ArrayBuffer[Int]): ColourBuff = new ColourBuff(buffer)
  }

  /** The argbValue must start with 0xFF if the default full opacity is required. So 0xFFFF0000 gives full opacity Red */
  def apply(argbValue: Int) = new Colour(argbValue)
  def fromInts(red: Int, green: Int, blue: Int, a: Int = 255): Colour = Colour(a * 256 * 256 * 256 + red * 256 * 256 + green * 256 + blue)
  def blackOrWhite(b: Boolean): Colour = if (b) Black else White

  def pairs: RArr[(Colour, Colour)] = ???
  
   /** named colors & values following CSS Color Module Level 4 - though names are UpperCamelCase here
       - plus 4 non standard colors: BrightSkyBlue, DarkYellow, LemonLime, LightRed*/
   val AntiqueWhite: Colour = new Colour(0xFFFAEBD7)
   val Aqua: Colour = new Colour(0xFF00FFFF)
   val Aquamarine = new Colour(0xFF7FFFD4)
   val Azure: Colour = new Colour(0xFFF0FFFF)
   val Beige: Colour = new Colour(0xFFF5F5DC)
   val Bisque: Colour = new Colour(0xFFFFE4C4)
   val Black: Colour = new Colour(0xFF000000)
   val BlanchedAlmond = new Colour(0xFFFFEBCD)
   val Blue: Colour = new Colour(0xFF0000FF)
   val BlueViolet: Colour = new Colour(0xFF8A2BE2)
   val BrightSkyBlue: Colour = new Colour(0xFFF0F9FF)
   val Brown: Colour = new Colour(0xFFA52A2A)
   val BurlyWood: Colour = new Colour(0xFFDEB887)
   val CadetBlue: Colour = new Colour(0xFF5F9EA0)
   val Chartreuse: Colour = new Colour(0xFF7FFF00)
   val Chocolate: Colour = new Colour(0xFFD2691E)
   val Coral: Colour = new Colour(0xFFFF7F50)
   val CornflowerBlue: Colour = new Colour(0xFF6495ED)
   val Cornsilk: Colour = new Colour(0xFFFFF8DC)
   val Crimson: Colour = new Colour(0xFFDC143C)
   val Cyan: Colour = new Colour(0xFF00FFFF)
   val DarkBlue: Colour = new Colour(0xFF00008B)
   val DarkCyan: Colour = new Colour(0xFF008B8B)
   val DarkGoldenRod: Colour = new Colour(0xFFB8860B)
   val DarkGray: Colour = new Colour(0xFFA9A9A9)
   val DarkGrey: Colour = new Colour(0xFFA9A9A9)
   val DarkGreen: Colour = new Colour(0xFF006400)
   val DarkKhaki: Colour = new Colour(0xFFBDB76B)
   val DarkMagenta: Colour = new Colour(0xFF8B008B)
   val DarkOliveGreen: Colour = new Colour(0xFF556B2F)
   val DarkOrange: Colour = new Colour(0xFFFF8C00)
   val DarkOrchid: Colour = new Colour(0xFF9932CC)
   val DarkRed: Colour = new Colour(0xFF8B0000)
   val DarkSalmon: Colour = new Colour(0xFFE9967A)
   val DarkSeaGreen: Colour = new Colour(0xFF8FBC8F)
   val DarkSlateBlue: Colour = new Colour(0xFF483D8B)
   val DarkSlateGray: Colour = new Colour(0xFF2F4F4F)
   val DarkSlateGrey: Colour = new Colour(0xFF2F4F4F)
   val DarkTurquoise: Colour = new Colour(0xFF00CED1)
   val DarkViolet: Colour = new Colour(0xFF9400D3)
   val DarkYellow: Colour = new Colour(0xFFEEEE00)
   val DeepPink: Colour = new Colour(0xFFFF1493)
   val DeepSkyBlue: Colour = new Colour(0xFF00BFFF)
   val DimGray: Colour = new Colour(0xFF696969)
   val DimGrey: Colour = new Colour(0xFF696969)
   val DodgerBlue: Colour = new Colour(0xFF1E90FF)
   val FireBrick: Colour = new Colour(0xFFB22222)
   val FloralWhite: Colour = new Colour(0xFFFFFAF0)
   val ForestGreen: Colour = new Colour(0xFF228B22)
   val Fuchsia: Colour = new Colour(0xFFFF00F0)
   val Gainsboro: Colour = new Colour(0xFFDCDCDC)
   val GhostWhite: Colour = new Colour(0xFFF8F8FF)
   val Gold: Colour = new Colour(0xFFFFD700)
   val GoldenRod: Colour = new Colour(0xFFDAA520)
   val Gray: Colour = new Colour(0xFF808080)
   val Grey: Colour = new Colour(0xFF808080)
   val Green: Colour = new Colour(0xFF008000)
   val GreenYellow: Colour = new Colour(0xFFADFF2F)
   val HoneyDew: Colour = new Colour(0xFFF0FFF0)
   val HotPink: Colour = new Colour(0xFFFF69B4)
   val IndianRed: Colour = new Colour(0xFFCD5C5C)
   val Indigo: Colour = new Colour(0xFF4B0082)
   val Ivory: Colour = new Colour(0xFFFFFFF0)
   val Khaki: Colour = new Colour(0xFFF0E68C)
   val Lavender: Colour = new Colour(0xFFE6E6FA)
   val LavenderBlush: Colour = new Colour(0xFFFFF0F5)
   val LawnGreen: Colour = new Colour(0xFF7CFC00)
   val LemonChiffon: Colour = new Colour(0xFFFFFACD)
   val LemonLime: Colour = new Colour(0xFFBACD22)
   val LightBlue: Colour = new Colour(0xFFADD8E6)
   val LightCoral: Colour = new Colour(0xFFF08080)
   val LightCyan: Colour = new Colour(0xFFE0FFFF)
   val LightGoldenRodYellow: Colour = new Colour(0xFFFAFAD2)
   val LightGreen: Colour = new Colour(0xFF90EE90)
   val LightGray: Colour = new Colour(0xFFD3D3D3)
   val LightGrey: Colour = new Colour(0xFFD3D3D3)
   val LightPink: Colour = new Colour(0xFFFFB6C1)
   val LightRed: Colour = new Colour(0xFFFF7755)
   val LightSalmon: Colour = new Colour(0xFFFFA07A)
   val LightSeaGreen: Colour = new Colour(0xFF20B2AA)
   val LightSkyBlue: Colour = new Colour(0xFF87CEFA)
   val LightSlateGray: Colour = new Colour(0xFF778899)
   val LightSlateGrey: Colour = new Colour(0xFF778899)
   val LightSteelBlue: Colour = new Colour(0xFFB0C4DE)
   val LightYellow: Colour = new Colour(0xFFFFFFE0)
   val Lime: Colour = new Colour(0xFF00FF00)
   val LimeGreen: Colour = new Colour(0xFF32CD32)
   val Linen: Colour = new Colour(0xFFFAF0E6)
   val Magenta: Colour = new Colour(0xFFFF00FF)
   val Maroon: Colour = new Colour(0xFF800000)
   val MediumAquaMarine: Colour = new Colour(0xFF66CDAA)
   val MediumBlue: Colour = new Colour(0xFF0000CD)
   val MediumOrchid: Colour = new Colour(0xFFBA55D3)
   val MediumPurple: Colour = new Colour(0xFF9370DB)
   val MediumSeaGreen: Colour = new Colour(0xFF3CB371)
   val MediumSlateBlue: Colour = new Colour(0xFF7B68EE)
   val MediumSpringGreen: Colour = new Colour(0xFF00FA9A)
   val MediumTurquoise: Colour = new Colour(0xFF48D1CC)
   val MediumVioletRed: Colour = new Colour(0xFFC71585)
   val MidnightBlue: Colour = new Colour(0xFF191970)
   val MintCream: Colour = new Colour(0xFFF5FFFA)
   val MistyRose: Colour = new Colour(0xFFFFE4E1)
   val Moccasin: Colour = new Colour(0xFFFFE4B5)
   val NavajoWhite: Colour = new Colour(0xFFFFDEAD)
   val Navy: Colour = new Colour(0xFF000080)
   val OldLace: Colour = new Colour(0xFFFDF5E6)
   val Olive: Colour = new Colour(0xFF808000)
   val OliveDrab: Colour = new Colour(0xFF6B8E23)
   val Orange: Colour = new Colour(0xFFFFA500)
   val OrangeRed: Colour = new Colour(0xFFFF4500)
   val Orchid: Colour = new Colour(0xFFDA70D6)
   val PaleGoldenRod: Colour = new Colour(0xFFEEE8AA)
   val PaleGreen: Colour = new Colour(0xFF98FB98)
   val PaleTurquoise: Colour = new Colour(0xFFAFEEEE)
   val PaleVioletRed: Colour = new Colour(0xFFDB7093)
   val PapayaWhip: Colour = new Colour(0xFFFFEFD5)
   val PeachPuff: Colour = new Colour(0xFFFFDAB9)
   val Peru: Colour = new Colour(0xFFCD853F)
   val Pink: Colour = new Colour(0xFFFFC0CB)
   val Plum: Colour = new Colour(0xFFDDA0DD)
   val PowderBlue: Colour = new Colour(0xFFB0E0E6)
   val Purple: Colour = new Colour(0xFF800080)
   val RebeccaPurple: Colour = new Colour(0xFF663399)
   val Red: Colour = new Colour(0xFFFF0000)
   val RosyBrown: Colour = new Colour(0xFFBC8F8F)
   val RoyalBlue: Colour = new Colour(0xFF4169E1)
   val SaddleBrown: Colour = new Colour(0xFF8B4513)
   val Salmon: Colour = new Colour(0xFFFA8072)
   val SandyBrown: Colour = new Colour(0xFFF4A460)
   val SeaGreen: Colour = new Colour(0xFF2E8B57)
   val SeaShell: Colour = new Colour(0xFFFFF5EE)
   val Sienna: Colour = new Colour(0xFFA0522D)
   val Silver: Colour = new Colour(0xFFC0C0C0)
   val SkyBlue: Colour = new Colour(0xFF87CEEB)
   val SlateBlue: Colour = new Colour(0xFF6A5ACD)
   val SlateGray: Colour = new Colour(0xFF708090)
   val SlateGrey: Colour = new Colour(0xFF708090)
   val Snow: Colour = new Colour(0xFFFFFAFA)
   val SpringGreen: Colour = new Colour(0xFF00FF7F)
   val SteelBlue: Colour = new Colour(0xFF4682B4)
   val Tan: Colour = new Colour(0xFFD2B48C)
   val Teal: Colour = new Colour(0xFF008080)
   val Thistle: Colour = new Colour(0xFFD8BFD8)
   val Tomato: Colour = new Colour(0xFFFF6347)
   val Turquoise: Colour = new Colour(0xFF40E0D0)
   val Violet: Colour = new Colour(0xFFEE82EE)
   val Wheat: Colour = new Colour(0xFFF5DEB3)
   val White: Colour = new Colour(0xFFFFFFFF)
   val WhiteSmoke: Colour = new Colour(0xFFF5F5F5)
   val Yellow: Colour = new Colour(0xFFFFFF00)
   val YellowGreen: Colour = new Colour(0xFF9ACD32)
   
   val strToValue: Map[String, Colour] = Map(
("AntiqueWhite", AntiqueWhite), ("Aqua", Aqua), ("Aquamarine", Aquamarine), ("Azure", Azure), ("Beige", Beige), ("Bisque", Bisque), ("Black", Black),
("BlanchedAlmond", BlanchedAlmond), ("Blue", Blue), ("BlueViolet", BlueViolet), ("BrightSkyBlue", BrightSkyBlue), ("Brown", Brown),
("BurlyWood", BurlyWood), ("CadetBlue", CadetBlue), ("Chartreuse", Chartreuse), ("Chocolate", Chocolate), ("Coral", Coral),
("CornflowerBlue", CornflowerBlue), ("Cornsilk", Cornsilk), ("Crimson", Crimson),("Cyan", Cyan), ("DarkBlue", DarkBlue), ("DarkCyan", DarkCyan),
("DarkGoldenRod", DarkGoldenRod), ("DarkGray", DarkGray), ("DarkGrey", DarkGrey), ("DarkGreen", DarkGreen), ("DarkKhaki", DarkKhaki),
("DarkMagenta", DarkMagenta), ("DarkOliveGreen", DarkOliveGreen), ("DarkOrange", DarkOrange), ("DarkOrchid", DarkOrchid), ("DarkRed", DarkRed),
("DarkSalmon", DarkSalmon), ("DarkSeaGreen", DarkSeaGreen), ("DarkSlateBlue", DarkSlateBlue), ("DarkSlateGray", DarkSlateGray),
("DarkSlateGrey", DarkSlateGrey), ("DarkTurquoise", DarkTurquoise), ("DarkViolet", DarkViolet), ("DarkYellow", DarkYellow),
("DeepPink", DeepPink), ("DeepSkyBlue", DeepSkyBlue), ("DimGray", DimGray), ("DimGrey", DimGrey), ("DodgerBlue", DodgerBlue),
("FireBrick", FireBrick), ("FloralWhite", FloralWhite), ("ForestGreen", ForestGreen), ("Fuchsia", Fuchsia), ("Gainsboro", Gainsboro),
("GhostWhite", GhostWhite), ("Gold", Gold), ("GoldenRod", GoldenRod), ("Gray", Gray), ("Grey", Gray), ("Green", Green), ("GreenYellow", GreenYellow),
("HoneyDew", HoneyDew), ("HotPk", HotPink), ("IndianRed", IndianRed), ("Indigo", Indigo), ("Ivory", Ivory), ("Khaki", Khaki), ("Lavender", Lavender),
("LavenderBlush", LavenderBlush), ("LawnGreen", LawnGreen), ("LemonChiffon", LemonChiffon), ("LemonLime", LemonLime),
("LightBlue", LightBlue), ("LightCoral", LightCoral), ("LightCyan", LightCyan), ("LightGoldenRodYellow", LightGoldenRodYellow),
("LightGreen", LightGreen), ("LightGray", LightGray), ("LightGrey", LightGrey), ("LightPink", LightPink), ("LightRed", LightRed),
("LightSalmon", LightSalmon), ("LightSeaGreen", LightSeaGreen), ("LightSkyBlue", LightSkyBlue), ("LightSlateGray", LightSlateGray),
("LightSlateGrey", LightSlateGrey), ("LightSteelBlue", LightSteelBlue), ("LightYellow", LightYellow), ("Lime", Lime), ("LimeGreen", LimeGreen),
("Linen", Linen), ("Magenta", Magenta), ("Maroon", Maroon), ("MediumAquaMarine", MediumAquaMarine), ("MediumBlue", MediumBlue),
("MediumOrchid", MediumOrchid), ("MediumPurple", MediumPurple), ("MediumSeaGreen", MediumSeaGreen),("MediumSlateBlue", MediumSlateBlue),
("MediumSpringGreen", MediumSpringGreen), ("MediumTurquoise", MediumTurquoise), ("MediumVioletRed", MediumVioletRed), ("MidnightBlue", MidnightBlue),
("MintCream", MintCream), ("MistyRose", MistyRose),
("Moccasin", Moccasin), ("NavajoWhite", NavajoWhite), ("Navy", Navy), ("OldLace", OldLace), ("Olive", Olive), ("OliveDrab", OliveDrab), ("Orange", Orange),
("OrangeRed", OrangeRed), ("Orchid", Orchid), ("PaleGoldenRod", PaleGoldenRod), ("PaleGreen", PaleGreen), ("PaleTurquoise", PaleTurquoise),
("PaleVioletRed", PaleVioletRed), ("PapayaWhip", PapayaWhip), ("PeachPuff", PeachPuff), ("Peru", Peru), ("Pink", Pink), ("Plum", Plum),
("PowderBlue", PowderBlue), ("Purple", Purple), ("RebeccaPurple", RebeccaPurple), ("Red", Red), ("RosyBrown", RosyBrown), ("RoyalBlue", RoyalBlue), ("SaddleBrown", SaddleBrown),
("Salmon", Salmon), ("SandyBrown", SandyBrown), ("SeaGreen", SeaGreen), ("SeaShell", SeaShell), ("Sienna", Sienna), ("Silver", Silver),
("SkyBlue", SkyBlue), ("SlateBlue", SlateBlue), ("SlateGray", SlateGray), ("SlateGrey", SlateGrey), ("Snow", Snow), ("SpringGreen", SpringGreen), ("SteelBlue", SteelBlue),
("Tan", Tan), ("Teal", Teal), ("Thistle", Thistle), ("Tomato", Tomato), ("Turquoise", Turquoise), ("Violet", Violet), ("Wheat", Wheat),
("White", White), ("WhiteSmoke", WhiteSmoke),("Yellow", Yellow), ("YellowGreen", YellowGreen)    
)
  val valueToStr: Map[Colour, String] = strToValue.map(p => (p._2, p._1))
}