/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for Show for product types. */
trait ShowProduct extends Show
{
  def strs(way: Show.Way, decimalPlaces: Int): Strings
  def names: Strings

  override def show(way: Show.Way, decimalPlaces: Int): String =
  { def semisStr = strs(Show.Commas, decimalPlaces).mkStr("; ")

    way match
    { case Show.Semis => semisStr
    case Show.Commas => strs(Show.Standard, decimalPlaces).mkStr(", ")
    case Show.StdFields =>
    { val inner = names.zipMap(strs(Show.Standard, decimalPlaces))((n, s) => n + " = " + s).mkStr(", ")
      typeStr + inner.enParenth
    }
    case _ => typeStr.appendParenth(semisStr)
    }
  }

  override def str: String = show(Show.Standard, 1)
}

/** Trait for Show for product of 2 elements. */
trait Show2[A1, A2] extends ShowProduct
{ def name1: String
  def name2: String
  def names: Strings = Strings(name1, name2)
  def arg1: A1
  def arg2: A2
  implicit def ev1: ShowT[A1]
  implicit def ev2: ShowT[A2]
  def strs(way: Show.Way, decimalPlaces: Int): Strings = Strings(ev1.showT(arg1, way, decimalPlaces), ev2.showT(arg2, way, decimalPlaces))
}