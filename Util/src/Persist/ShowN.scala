/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for Show for product types. This trait is implemented directly by the type in question, unlike the corresponding [[ShowNT]] trait
 * which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 * [[ShowNT]]. */
trait ShowN extends Any with ShowDec
{ /** A [[Strings]] Arr of the element names of this Show Product class. */
  def elemNames: Strings

  /** A [[Strings]] Arr of the element type names of this Show Product class. */
  def elemTypeNames: Strings

  override def str: String = show(ShowStandard)

  /** A [[Strings]] Arr collection  of the show methods return values of the elements of this Show Product class. */
  //def showElemStrs(way: ShowStyle): Strings

  def showSemisNames: String =
    elemNames.zipMap(showElemStrs(ShowStandard))((n, s) => n + " = " + s).mkStr("; ")

  override def show(style: ShowStyle): String =
  { def semisStr = showElemStrs(ShowCommas).mkStr("; ")

    style match
    { case ShowSemis => semisStr
      case ShowCommas => showElemStrs(ShowStandard).mkStr(", ")
      case ShowParamNames => typeStr + showSemisNames.enParenth
      case ShowSemisNames => showSemisNames

    case ShowStdTypedFields =>
    { val inner = elemNames.zipMap2(elemTypeNames,showElemStrs(ShowStandard))((n, t, s) => n + ": " + t + " = " + s).mkStr("; ")
      typeStr + inner.enParenth
    }

    case _ => typeStr.appendParenth(semisStr)
    }
  }

  /** A [[Strings]] Arr collection  of the show methods return values of the elements of this Show Product class. */
  def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): Strings

  def showElemStrs(way: ShowStyle): Strings = showElemStrDecs(way, -1)

  def showSemisNameDecs(maxPlaces: Int = -1, minPlaces: Int = 0): String =
    elemNames.zipMap(showElemStrDecs(ShowStandard, maxPlaces))((n, s) => n + " = " + s).mkStr("; ")

  override def showDec(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
  { def semisStr = showElemStrDecs(ShowCommas, maxPlaces).mkStr("; ")

    style match
    { case ShowSemis => semisStr
    case ShowCommas => showElemStrDecs(ShowStandard, maxPlaces).mkStr(", ")
    case ShowParamNames => typeStr + showSemisNameDecs(maxPlaces, minPlaces).enParenth
    case ShowSemisNames => showSemisNameDecs(maxPlaces, minPlaces)

    case ShowStdTypedFields =>
    { val inner = elemNames.zipMap2(elemTypeNames,showElemStrDecs(ShowStandard, maxPlaces))((n, t, s) => n + ": " + t + " = " + s).mkStr("; ")
      typeStr + inner.enParenth
    }

    case _ => typeStr.appendParenth(semisStr)
    }
  }
}