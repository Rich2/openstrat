/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for Show for product types. This trait is implemented directly by the type in question, unlike the corresponding [[ShowProductT]] trait
 * which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 * [[ShowProductT]]. */
trait ShowProduct extends Any with Show
{
  def strs(way: Show.Way, decimalPlaces: Int): Strings
  def elemNames: Strings
  def elemTypeNames: Strings

  override def show(way: Show.Way, decimalPlaces: Int): String =
  { def semisStr = strs(Show.Commas, decimalPlaces).mkStr("; ")

    way match
    { case Show.Semis => semisStr
      case Show.Commas => strs(Show.Standard, decimalPlaces).mkStr(", ")

      case Show.StdFields =>
      { val inner = elemNames.zipMap(strs(Show.Standard, decimalPlaces))((n, s) => n + " = " + s).mkStr(", ")
        typeStr + inner.enParenth
      }

      case Show.StdTypedFields =>
      { val inner = elemNames.zipMap2(elemTypeNames,strs(Show.Standard, decimalPlaces))((n, t, s) => n + ": " + t + " = " + s).mkStr(", ")
        typeStr + inner.enParenth
      }

      case _ => typeStr.appendParenth(semisStr)
    }
  }

  override def str: String = show(Show.Standard, 1)
}

/** Trait for Show for product of 2 logical elements. This trait is implemented directly by the type in question, unlike the corresponding [[Show2T]]
 *  trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred
 *  over [[Show2T]]. */
trait Show2[A1, A2] extends Any with ShowProduct with Prod2[A1, A2]
{ def name1: String
  def name2: String
  def elemNames: Strings = Strings(name1, name2)
  //def arg1: A1
  //def arg2: A2
  implicit def ev1: ShowT[A1]
  implicit def ev2: ShowT[A2]
  def elemTypeNames: Strings = Strings(ev1.typeStr, ev2.typeStr)
  def strs(way: Show.Way, decimalPlaces: Int): Strings = Strings(ev1.showT(el1, way, decimalPlaces), ev2.showT(el2, way, decimalPlaces))
}

/** Trait for Show for product of 2 Ints. This trait is implemented directly by the type in question, unlike the corresponding [[Show2IntsT]]
 *  trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred
 *  over [[Show2T]]. */
trait Show2Ints extends Any with Show2[Int, Int]
{ final override implicit def ev1: ShowT[Int] = ShowT.intPersistImplicit
  final override implicit def ev2: ShowT[Int] = ShowT.intPersistImplicit
  final override def syntaxdepth: Int = 2
}

/** Trait for Show for product of 2 Doubles. This trait is implemented directly by the type in question, unlike the corresponding [[Show2DblsT]]
 *  trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred
 *  over [[Show2T]]. */
trait Show2Dbls extends Any with Show2[Double, Double] with Dbl2Elem with Approx[Double]
{ final override implicit def ev1: ShowT[Double] = ShowT.doublePersistImplicit
  final override implicit def ev2: ShowT[Double] = ShowT.doublePersistImplicit
  final override def syntaxdepth: Int = 2
}