/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML element. */
trait XmlElem extends XmlElemLike
{ //override def out(indent: Int = 0, maxLineLen: Int = 150): String = if (contents.empty) openAtts + "/>"
  //else openUnclosed.nli(indent + 2) + contents.foldStr(_.out(indent + 2, 150), "\n" + (indent + 2).spaces).nli(indent) + closeTag
}

trait XmlMulti extends XmlElem with XmlLikeMulti

trait XmlNoAtts extends XmlElem
{ override def attribs: RArr[XmlAtt] = RArr()
}

trait XmlMultiNoAtts extends XmlMulti with XmlNoAtts

/** A Simple inline XML element. */
trait XmlStr extends XmlNoAtts with XmlLikeStr
{

}