/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat


/** Trait for Show for product types. This trait is implemented directly by the type in question, unlike the corresponding [[ShowProductDecT]] trait
 * which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 * [[ShowProductDecT]]. */
trait ShowProductDec extends Any with ShowPrec
{
  /** A [[Strings]] Arr collection  of the show methods return values of the elements of this Show Product class. */
  def showElemStrs(way: ShowStyle, decimalPlaces: Int): Strings

  /** A [[Strings]] Arr of the element names of this Show Product class. */
  def elemNames: Strings

  /** A [[Strings]] Arr of the element type names of this Show Product class. */
  def elemTypeNames: Strings

  def showSemisNames(maxPlaces: Int = -1, minPlaces: Int = 0): String =
    elemNames.zipMap(showElemStrs(ShowStandard, maxPlaces))((n, s) => n + " = " + s).mkStr("; ")

  override def showDec(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
  { def semisStr = showElemStrs(ShowCommas, maxPlaces).mkStr("; ")

    style match
    { case ShowSemis => semisStr
      case ShowCommas => showElemStrs(ShowStandard, maxPlaces).mkStr(", ")
      case ShowParamNames => typeStr + showSemisNames(maxPlaces, minPlaces).enParenth
      case ShowSemisNames => showSemisNames(maxPlaces, minPlaces)

      case ShowStdTypedFields =>
      { val inner = elemNames.zipMap2(elemTypeNames,showElemStrs(ShowStandard, maxPlaces))((n, t, s) => n + ": " + t + " = " + s).mkStr(", ")
        typeStr + inner.enParenth
      }

      case _ => typeStr.appendParenth(semisStr)
    }
  }

  override def str: String = showDec(ShowStandard, 1, 0)
}

/** The base trait for the persistence of algebraic product types, including case classes. */
trait ShowProductT[R] extends ShowCompoundT[R]// with ShowDecT[R]
{
  def strs(obj: R, way: ShowStyle): Strings

  override def showT(obj: R, style: ShowStyle): String =
  { def semisStr = strs(obj, ShowCommas).mkStr("; ")

    style match
    { case ShowUnderScore => "_"
    case ShowSemis => semisStr
    case ShowCommas => strs(obj, ShowStandard).mkStr(", ")
    case _ => typeStr.appendParenth(semisStr)
    }
  }
}

/** The base trait for the persistence of algebraic product types, including case classes. */
trait ShowProductDecT[R] extends ShowCompoundT[R] with ShowDecT[R]
{
  def strDecs(obj: R, way: ShowStyle, decimalPlaces: Int): Strings

  override def showDecT(obj: R, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
  { def semisStr = strDecs(obj, ShowCommas, maxPlaces).mkStr("; ")

    style match
    { case ShowUnderScore => "_"
      case ShowSemis => semisStr
      case ShowCommas => strDecs(obj, ShowStandard, maxPlaces).mkStr(", ")
      case _ => typeStr.appendParenth(semisStr)
    }
  }
}

/** The base trait for the persistence of algebraic product types, including case classes. Note the arity of the product, its size is based on the
 *  number of logical parameters. For example, a LineSeg is a product 2, it has a start point and an end point, although its is stored as 4 parameters
 *  xStart, yStart, xEnd, yEnd. */
trait PersistProductDec[R] extends PersistDec[R] with ShowProductDecT[R]

trait PersistShowProduct[R <: ShowProductDec] extends PersistProductDec[R]