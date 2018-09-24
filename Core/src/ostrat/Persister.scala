/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Persistence trait for traits / class that you control */
trait Persister extends Any
{
  def typeSym: Symbol
  def typeStr: String = typeSym.name
  def persist: String
  final override def toString = persist
  def persistD2(d1: Double, d2: Double): String = typeStr + ostrat.commaedObjs(d1, d2).enParenth
  def persist3[V1: Persist, V2: Persist, V3: Persist]: String = ???
}

trait PersisterD2 extends ProdD2 with Persister
{
  final override def persist = typeStr + ostrat.commaedObjs(_1, _2).enParenth
}