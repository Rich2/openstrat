/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for Show for product types. This trait is implemented directly by the type in question, unlike the corresponding [[ShowDecNT]] trait
 * which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 * [[ShowDecNT]]. */
trait ShowDecN extends Any with ShowN with ShowDec
{
  /** A [[Strings]] Arr collection  of the show methods return values of the elements of this Show Product class. */
  def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): Strings

  override def showElemStrs(way: ShowStyle): Strings = showElemStrDecs(way, -1)

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
      { val inner = elemNames.zipMap2(elemTypeNames,showElemStrDecs(ShowStandard, maxPlaces))((n, t, s) => n + ": " + t + " = " + s).mkStr(", ")
        typeStr + inner.enParenth
      }

      case _ => typeStr.appendParenth(semisStr)
    }
  }
}

/** The base trait for the persistence of algebraic product types, including case classes. */
trait ShowDecNT[R] extends ShowNT[R] with ShowDecT[R]
{
  def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): Strings

  override def strs(obj: R, way: ShowStyle): Strings = strDecs(obj, way, -1)

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

trait ShowShowDecNT[R <: ShowDecN] extends ShowDecNT[R] with ShowShowT[R]
{
  override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): Strings = obj.showElemStrDecs(way, maxPlaces)
}

/** The base trait for the persistence of algebraic product types, including case classes. Note the arity of the product, its size is based on the
 *  number of logical parameters. For example, a LineSeg is a product 2, it has a start point and an end point, although its is stored as 4 parameters
 *  xStart, yStart, xEnd, yEnd. */
trait PersistDecN[R] extends PersistDec[R] with ShowDecNT[R]

trait PersistShowN[R <: ShowDecN] extends PersistDecN[R]