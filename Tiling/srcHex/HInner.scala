/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

trait HInner

/** Island or similar terrain where the main land area is surrounded by sea. */
trait HInner6 extends HInner

trait HInner5 extends HInner
{
  def outSideNum: Int
}