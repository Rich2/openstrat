/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** Offset Square tiles grid coordinate. */
trait OSCoord extends SqBaseCoord

class OSCen(val r: Int, val c: Int) extends OSCoord
{ override def typeStr: String = "OSCen"
}

class OSSide(val r: Int, val c: Int) extends OSCoord
{ override def typeStr: String = "OSSide"
}

class OSvert(val r: Int, val c: Int) extends OSCoord
{ override def typeStr: String = "OSvert"
}