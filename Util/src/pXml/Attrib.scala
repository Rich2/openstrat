/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pXml

trait Attrib
{ def name: String
  def valueStr: String
  def str: String = name + "=" + valueStr.enquote
}
