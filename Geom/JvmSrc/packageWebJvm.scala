/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb;
import pParse.*, utiljvm.*

/** This package is for Java byte code targets. */
package object webjvm
{ /** The resource folders and hence the developer settings folder are set in the build tool Sbt and Mill. They are not set in the code. */
  lazy val devSettingsStatements: ThrowMonRArr[Statement] = statementsFromResource("DevSettings.rson")

  /** Find a setting of the given name and return its Expr from the file DevSettings.rson. */
  def findDevSettingExpr(settingStr: String): ThrowMon[AssignMemExpr] = devSettingsStatements.flatMap(_.findSettingExpr(settingStr))

  /** Find a setting of the given name and type from the file DevSettings.rson. */
  def findDevSetting[A: Unshow](settingStr: String): ThrowMon[A] = devSettingsStatements.flatMap(_.findSetting(settingStr))

  /** Find a setting of the given name and type from the file DevSettings.rson, else return the given default value.. */
  def findDevSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = devSettingsStatements.flatMap(_.findSetting(settingStr)).getElse(elseValue)

  /** Find the [[String]] for the identifier value of o setting of the given name in the file DevSettings.rson. */
  def findDevSettingIdStr(settingStr: String): ThrowMon[String] = devSettingsStatements.flatMap(_.findSettingId(settingStr))

  /** Find the project path. */
  def projPathFind: ThrowMon[ProjPath] = findDevSetting[DirsAbs]("projPath").map(_.projPath)

  /** If the project path can be found in Dev/User/DevSettings.rson do the side effect function. */
  def projPathDo(f: ProjPath => Unit): Unit = projPathFind.forFold { err => deb(err.toString) } { path => f(path) }

  /** If the project path can be found in Dev/User/DevSettings.rson do the side effect function. */
  def stagingPathDo(f: DirsAbs => Unit): Unit = findDevSetting[DirsAbs]("stagingPath").forFold { err => deb(err.toString) } { path => f(path) }

  /** Possible path to the openstrat directory, if it can be found in Dev/User/DevSettings.rson file. */
  def openstratPath: ThrowMon[DirsAbs] = findDevSetting[DirsAbs]("projPath")

  /** Possible path to the staging directory for openstrat artifacts, if it can be found in Dev/User/DevSettings.rson file. */
  def stagingPathFind: ThrowMon[DirsAbs] = findDevSetting[DirsAbs]("stagingPath")

}