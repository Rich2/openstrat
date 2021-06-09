/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for Show for product types. This trait is implemented directly by the type in question, unlike the corresponding [[ShowProductT]] trait
 * which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 * [[ShowProductT]]. */
trait ShowProduct extends Any with Show
{
  /** A [[Strings]] Arr collection  of the show methods return values of the elements of this Show Product class. */
  def shows(way: Show.Way, decimalPlaces: Int): Strings

  /** A [[Strings]] Arr of the element names of this Show Product class. */
  def elemNames: Strings

  /** A [[Strings]] Arr of the element type names of this Show Product class. */
  def elemTypeNames: Strings

  override def show(way: Show.Way, maxPlaces: Int, minPlaces: Int): String =
  { def semisStr = shows(Show.Commas, maxPlaces).mkStr("; ")

    way match
    { case Show.Semis => semisStr
      case Show.Commas => shows(Show.Standard, maxPlaces).mkStr(", ")

      case Show.StdFields =>
      { val inner = elemNames.zipMap(shows(Show.Standard, maxPlaces))((n, s) => n + " = " + s).mkStr(", ")
        typeStr + inner.enParenth
      }

      case Show.StdTypedFields =>
      { val inner = elemNames.zipMap2(elemTypeNames,shows(Show.Standard, maxPlaces))((n, t, s) => n + ": " + t + " = " + s).mkStr(", ")
        typeStr + inner.enParenth
      }

      case _ => typeStr.appendParenth(semisStr)
    }
  }

  override def str: String = show(Show.Standard, 1, 0)
}