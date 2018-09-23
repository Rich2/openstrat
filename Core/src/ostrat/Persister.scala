/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Persistence trait for traits / class that you control */
trait Persister
{
  def typeSym: Symbol
  def persist: String
}

trait PersisterCompound extends Persister
{
  def memStrs: List[String]
}