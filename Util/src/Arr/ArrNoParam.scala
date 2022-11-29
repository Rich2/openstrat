/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, unchecked.uncheckedVariance


/** This trait is for all the [[ArrSingle]] classes except [[RArr]]. All the final classes of this
 * trait have no type parameters. The primary motivation of this trait is to allow common
 * extractors. */
trait ArrNoParam[A] extends Any with ArrSingle[A]
{ type ThisT <: ArrNoParam[A]

  def drop(n: Int): ThisT

  final def tail: ThisT = drop(1)

  def dropRight(n: Int): ThisT = ???

  final def init: ThisT = dropRight(1)

  /** Reverses the order of the elements of this sequence. */
  def reverse: ThisT

  /** append. Appends an [[Arr]] of the same final type of this [[Arr]]. */
  @targetName("appendArr") def ++(operand: ThisT): ThisT

  /** append. appends element to this [[Arr]]. */
  @targetName("append") def +%(operand: A): ThisT
}