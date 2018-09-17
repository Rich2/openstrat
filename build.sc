// build.sc
import mill._, scalalib._, scalajslib._

trait Common extends ScalaModule {
  def scalaVersion = "2.12.6"
  def scalacOptions = Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint", "-Yno-adapted-args")
}  

object Macros extends Common
{  
  def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
}

object Core extends Common
{  
  def moduleDeps = Seq(Macros)
  object test extends Tests
  {
    def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.5")
    def testFrameworks = Seq("utest.runner.Framework")
  }
}

object FxStrat extends Common
 {
  def ivyDeps = Agg(ivy"org.scalafx::scalafx:8.0.144-R12")
  def moduleDeps = Seq(Core)
  def mainClass = Some("ostrat.pFx.DevApp")
    
}

object JsStrat extends ScalaJSModule with Common {
  def scalaJSVersion = "0.6.25"
  def ivyDeps = Agg(ivy"org.scala-js::scalajs-dom_sjs0.6:0.9.6")
  def moduleDeps = Seq(Core)
  def sources = T.sources( millSourcePath / 'src, millSourcePath / 'srcPlay )  

}
