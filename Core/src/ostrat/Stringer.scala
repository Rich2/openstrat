/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** A variation on the Show trait for traits, classes and objects that you control. Named after the popular "The Wire" character. You will still need to
  * create an implicit Persist object in the companion object of your type, but this trait does allow you to automatically delegate toString to the
  * String / Persist implemmentation. */
trait Stringer extends Any
{ def typeSym: Symbol
  def typeStr: String = typeSym.name
  def str: String
  final override def toString = str
  def persistD2(d1: Double, d2: Double): String = typeStr + ostrat.commaedObjs(d1, d2).enParenth
  def persist2[T1, T2](v1: T1, v2: T2)(implicit ev1: Persist[T1], ev2: Persist[T2]): String =
    typeStr + (ev1.persist(v1).semicolonAppend(ev2.persist(v2))).enParenth
  def persist3[T1, T2, T3](v1: T1, v2: T2, v3: T3)(implicit ev1: Persist[T1], ev2: Persist[T2], ev3: Persist[T3]): String =
    typeStr + (ev1.persist(v1) + ev2.persist(v2) + ev3.persist(v3)).enParenth
}

trait StringerD2 extends Any with ProdD2 with Stringer { final override def str = typeStr + ostrat.commaedObjs(_1, _2).enParenth }
trait StringerI2 extends Any with ProdI2 with Stringer { final override def str = typeStr + ostrat.commaedObjs(_1, _2).enParenth }
