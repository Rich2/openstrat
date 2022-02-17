/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for Show for product types. This trait is implemented directly by the type in question, unlike the corresponding [[ShowDecNT]] trait
 * which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 * [[ShowDecNT]]. */
trait ShowN extends Any with Show
{ /** A [[Strings]] Arr of the element names of this Show Product class. */
  def elemNames: Strings

  /** A [[Strings]] Arr of the element type names of this Show Product class. */
  def elemTypeNames: Strings

  override def str: String = show(ShowStandard)

  /** A [[Strings]] Arr collection  of the show methods return values of the elements of this Show Product class. */
  def showElemStrs(way: ShowStyle): Strings

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
    { val inner = elemNames.zipMap2(elemTypeNames,showElemStrs(ShowStandard))((n, t, s) => n + ": " + t + " = " + s).mkStr(", ")
      typeStr + inner.enParenth
    }

    case _ => typeStr.appendParenth(semisStr)
    }
  }
}

/** The base trait for the persistence of algebraic product types, including case classes. */
trait ShowNT[R] extends ShowCompoundT[R]
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

trait ShowShowNT[R <: ShowN] extends ShowNT[R] with ShowShowT[R]
{ override def strs(obj: R, way: ShowStyle): Strings = obj.showElemStrs(way)
}

/** The base trait for the persistence of algebraic product types, including case classes. Note the arity of the product, its size is based on the
 *  number of logical parameters. For example, a LineSeg is a product 2, it has a start point and an end point, although its is stored as 4 parameters
 *  xStart, yStart, xEnd, yEnd. */
trait PersistN[R] extends Persist[R] with ShowNT[R]

trait PersistShowN[R <: ShowN] extends PersistN[R] with ShowShowNT[R]