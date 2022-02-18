/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

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

trait ShowShowNT[R <: ShowDecN] extends ShowDecNT[R] with ShowShowT[R]
{ override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): Strings = obj.showElemStrDecs(way, maxPlaces)
}