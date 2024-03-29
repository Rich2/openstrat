/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for Show for product types. This trait is implemented directly by the type in question, unlike the corresponding [[ShowNFixed]] trait
 * which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 * [[ShowNFixed]]. */
trait TellN extends Any with Tell
{ /** A [[StrArr]] Arr of the element names of this Show Product class. */
  def paramNames: StrArr

  /** A [[StrArr]] Arr of the element type names of this Show Product class. */
  def elemTypeNames: StrArr

  override def str: String = tell(ShowStdNoSpace)

  /** A [[StrArr]] Arr collection  of the show methods return values of the elements of this Show Product class. */
  def tellElemStrs(way: ShowStyle, decimalPlaces: Int = -1, minPlaces: Int = 0): StrArr

  def tellSemisNames(maxPlaces: Int = -1, minPlaces: Int = 0): String =
    paramNames.zipMap(tellElemStrs(ShowStdNoSpace, maxPlaces, minPlaces))((n, s) => n + " = " + s).mkStr("; ")

  override def tell(style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String =
  {
    def semisStr: String = tellElemStrs(ShowCommas, maxPlaces, minPlaces).mkStr("; ")

    style match
    { case ShowSemis => semisStr
      case ShowCommas => tellElemStrs(ShowCommas, maxPlaces).mkStr(", ")
      case ShowFieldNames => typeStr + tellSemisNames(maxPlaces, minPlaces).enParenth
      case ShowSemisNames => tellSemisNames(maxPlaces, minPlaces)

      case ShowStdTypedFields =>
      { val inner = paramNames.zipMap2(elemTypeNames,tellElemStrs(ShowStdNoSpace, maxPlaces))((n, t, s) => n + ": " + t + " = " + s).mkStr("; ")
        typeStr + inner.enParenth
      }

      case _ => typeStr.appendParenth(semisStr)
    }
  }
}