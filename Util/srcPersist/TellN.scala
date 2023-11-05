/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for Show for product types. This trait is implemented directly by the type in question, unlike the corresponding [[ShowN]] trait
 * which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 * [[ShowN]]. */
trait TellN extends Any with TellDec
{ /** A [[StrArr]] Arr of the element names of this Show Product class. */
  def paramNames: StrArr

  /** A [[StrArr]] Arr of the element type names of this Show Product class. */
  def elemTypeNames: StrArr

  override def str: String = tell(ShowStandard)

  def showSemisNames: String =
    paramNames.zipMap(showElemStrs(ShowStandard))((n, s) => n + " = " + s).mkStr("; ")

  override def tell(style: ShowStyle): String =
  { def semisStr = showElemStrs(ShowCommas).mkStr("; ")

    style match
    { case ShowSemis => semisStr
      case ShowCommas => showElemStrs(ShowStandard).mkStr(", ")
      case ShowFieldNames => typeStr + showSemisNames.enParenth
      case ShowSemisNames => showSemisNames

    case ShowStdTypedFields =>
    { val inner = paramNames.zipMap2(elemTypeNames,showElemStrs(ShowStandard))((n, t, s) => n + ": " + t + " = " + s).mkStr("; ")
      typeStr + inner.enParenth
    }

    case _ => typeStr.appendParenth(semisStr)
    }
  }

  /** A [[StrArr]] Arr collection  of the show methods return values of the elements of this Show Product class. */
  def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr

  def showElemStrs(way: ShowStyle): StrArr = showElemStrDecs(way, -1)

  def showSemisNameDecs(maxPlaces: Int = -1, minPlaces: Int = 0): String =
    paramNames.zipMap(showElemStrDecs(ShowStandard, maxPlaces))((n, s) => n + " = " + s).mkStr("; ")

  override def tellDec(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
  { def semisStr = showElemStrDecs(ShowCommas, maxPlaces).mkStr("; ")

    style match
    { case ShowSemis => semisStr
      case ShowCommas => showElemStrDecs(ShowCommas, maxPlaces).mkStr(", ")
      case ShowFieldNames => typeStr + showSemisNameDecs(maxPlaces, minPlaces).enParenth
      case ShowSemisNames => showSemisNameDecs(maxPlaces, minPlaces)

      case ShowStdTypedFields =>
      { val inner = paramNames.zipMap2(elemTypeNames,showElemStrDecs(ShowStandard, maxPlaces))((n, t, s) => n + ": " + t + " = " + s).mkStr("; ")
        typeStr + inner.enParenth
      }

      case _ => typeStr.appendParenth(semisStr)
    }
  }
}