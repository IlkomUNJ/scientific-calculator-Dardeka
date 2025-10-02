package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.AppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.TextUnit
import kotlin.math.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Calculator(Modifier.fillMaxSize())
            }
        }
    }
}

// Operators
enum class CalcOperator{
    IDLE,
    ADD,
    SUBTRACT,
    MULTIPLE,
    DIVIDE,
    PERCENT,
    RECIPROCAL,
    POW,
    LOG10,
    FACTOR,
    LN,
    SQRT,
    SIN,
    ASIN,
    COS,
    ACOS,
    TAN,
    ATAN
}

fun Calculate(val1: Double, val2: Double, operands: CalcOperator) : Double{
    return when(operands){
        CalcOperator.IDLE -> val1
        CalcOperator.ADD -> val1+val2
        CalcOperator.SUBTRACT -> val1-val2
        CalcOperator.MULTIPLE -> val1*val2
        CalcOperator.DIVIDE -> if(val2 != 0.0) val1/val2 else 0.0
        CalcOperator.PERCENT -> val1%val2
        CalcOperator.RECIPROCAL -> 1/val1
        CalcOperator.POW -> val1.pow(val2)
        CalcOperator.LOG10 -> log10(val1)
        CalcOperator.FACTOR -> factorial(val1.toInt())
        CalcOperator.LN -> {
            if (val1 >= 0) ln(val1) else Double.NaN
        }
        CalcOperator.SQRT -> {
            if (val1 >= 0) sqrt(val1) else Double.NaN
        }
        CalcOperator.SIN -> sin(val1)
        CalcOperator.COS -> cos(val1)
        CalcOperator.TAN -> tan(val1)
        CalcOperator.ASIN -> asin(val1)
        CalcOperator.ACOS -> acos(val1)
        CalcOperator.ATAN -> atan(val1)
    }
}


fun factorial(n: Int) : Double{
    var res = 1
    if (n < 0){
        return -1.0
    }else if (n > 20){
        return -1.0
    }else{
        for (i in 1..n){
            res = res*i
        }
    }
    return res.toDouble()
}


// Function for buttons
@Composable
fun CalcBtn(value: String, wid: Int, hei: Int, OnClick: ()-> Unit, colors: Color, textColors: Color){
    if(wid == 0 && hei == 0){
        Button(
            modifier = Modifier.padding(end = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors,
                contentColor = textColors
            ),
            shape = RoundedCornerShape(5.dp),
            onClick = OnClick
        ) { Text(value, fontWeight = FontWeight.Bold) }

    }
    else if (wid == 0 && hei > 0){
        Button(
            modifier = Modifier
                .padding(top = 4.dp, end = 0.dp)
                .height(hei.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors,
                contentColor = textColors
            ),
            shape = RoundedCornerShape(5.dp),
            onClick = OnClick
        ) { Text(value, fontWeight = FontWeight.Bold) }

    }else if (wid > 0 && hei == 0){
        Button(
            modifier = Modifier
                .padding(end = 10.dp)
                .width(wid.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors,
                contentColor = textColors,
            ),
            shape = RoundedCornerShape(5.dp),
            onClick = OnClick
        ) { Text(value, fontWeight = FontWeight.Bold) }

    }else{
        Button(
            modifier = Modifier
                .padding(end = 10.dp)
                .width(wid.dp)
                .height(hei.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors,
                contentColor = textColors
            ),
            shape = RoundedCornerShape(5.dp),
            onClick = OnClick
        ) { Text(value, fontWeight = FontWeight.Bold) }

    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier){
    var operate by remember { mutableStateOf(CalcOperator.IDLE) }
    var mode by remember {mutableStateOf("")}
    var prevVal by remember { mutableStateOf("") }
    var currentVal by remember { mutableStateOf("0") }

    Surface(modifier) {
        Column(modifier.padding( top = 50.dp, start = 15.dp, bottom = 50.dp, end = 15.dp), verticalArrangement = Arrangement.Center) {
            Text("Scientific Calculator - Chasio", fontWeight = FontWeight.Bold)
//            Display Calc
            Column(Modifier
                .height(120.dp)
                .fillMaxWidth().background(Color.LightGray)
                .border(2.dp, Color.Black, shape = RectangleShape), verticalArrangement = Arrangement.Center) {
                Row(Modifier.background(Color.LightGray), horizontalArrangement = Arrangement.SpaceBetween){
                    Text(mode, fontWeight = FontWeight.ExtraBold, modifier = Modifier.width(140.dp).padding(start = 10.dp, top = 10.dp).background(Color.Gray).border(width = 1.dp,
                        Color.Black), color = Color.White, textAlign = TextAlign.Center, maxLines = 1)
                    Spacer(Modifier)
                    Text(prevVal, modifier = Modifier
                        .height(60.dp).fillMaxWidth().padding(start = 15.dp, top = 10.dp, end = 15.dp), fontSize = 24.sp, textAlign = TextAlign.End, maxLines = 1)
                }
                Text(currentVal, modifier = Modifier
                    .height(60.dp)
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth(), fontSize = 24.sp, textAlign = TextAlign.End)
            }
//            Buttons
            Row(Modifier
                .padding(top = 25.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CalcBtn("Sin", 100, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.SIN else CalcOperator.SIN; mode = "Sin"}, Color(0xFFFF9800), Color.White)
                CalcBtn("Cos", 100, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.COS else CalcOperator.COS; mode = "Cos"}, Color(0xFFFF9800),Color.White)
                CalcBtn("Tan", 100, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.TAN else CalcOperator.TAN; mode = "Tan"}, Color(0xFFFF9800),Color.White)
            }
            Row(Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CalcBtn("Sin⁻¹", 100, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.ASIN else CalcOperator.ASIN; mode = "Sin⁻¹"}, Color(0xFFFF9800),Color.White)
                CalcBtn("Cos⁻¹", 100, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.ACOS else CalcOperator.ACOS; mode = "Cos⁻¹"}, Color(0xFFFF9800),Color.White)
                CalcBtn("Tan⁻¹", 100, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.ATAN else CalcOperator.ATAN; mode = "Tan⁻¹"}, Color(0xFFFF9800),Color.White)
            }
            Row(Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CalcBtn("1/x", 0, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.RECIPROCAL else CalcOperator.RECIPROCAL; mode = "Reciprocal"}, Color(0xFFFF9800),Color.White)
                CalcBtn("x!", 0, 0, {operate = if (operate == CalcOperator.IDLE) CalcOperator.FACTOR else CalcOperator.FACTOR; mode = "Factor" }, Color(0xFFFF9800),Color.White)
                CalcBtn("x^y", 0, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.POW else CalcOperator.POW; prevVal = currentVal; currentVal = "0"; mode = "Exponentiation"}, Color(0xFFFF9800),Color.White)
                CalcBtn("√x", 0, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.SQRT else CalcOperator.SQRT; mode = "SQRT"}, Color(0xFFFF9800),Color.White)
                CalcBtn("log", 0, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.LOG10 else CalcOperator.LOG10; mode = "Log 10"}, Color(0xFFFF9800),Color.White)
            }

            Row {
                Column(Modifier) {
                    Row(Modifier.padding(top = 5.dp)) {
                        CalcBtn("ln", 0, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.LN else CalcOperator.LN; prevVal = currentVal; currentVal = "0"; mode = "Ln"}, Color(0xFFFF9800),Color.White)
                        CalcBtn("%", 0, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.PERCENT else CalcOperator.PERCENT; prevVal = currentVal; currentVal = "0"; mode = "Modulo"}, Color(0xFFFF9800),Color.White)
                        CalcBtn("/", 0, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.DIVIDE else CalcOperator.DIVIDE; prevVal = currentVal; currentVal = "0"; mode = "Divide"}, Color(0xFFFF9800),Color.White)
                        CalcBtn("x", 0, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.MULTIPLE else CalcOperator.MULTIPLE; prevVal = currentVal; currentVal = "0"; mode = "Multiplication"}, Color(0xFFFF9800),Color.White)
                    }
                    Row(Modifier) {
//                Button angka
                        Column(Modifier) {
                            Row(Modifier) {
                                CalcBtn("7", 0, 0,
                                    {
                                        currentVal = if (currentVal == "0") "7" else currentVal + "7"
                                    }, Color.LightGray,Color.Black)
                                CalcBtn("8", 0, 0,
                                    {
                                        currentVal =
                                            if (currentVal == "0") "8" else currentVal + "8"
                                    }, Color.LightGray,Color.Black)
                                CalcBtn("9", 0, 0,
                                    {
                                        currentVal =
                                            if (currentVal == "0") "9" else currentVal + "9"
                                    }, Color.LightGray,Color.Black)

                            }
                            Row(Modifier) {
                                CalcBtn("4", 0, 0,
                                    {
                                        currentVal =
                                            if (currentVal == "0") "4" else currentVal + "4"
                                    }, Color.LightGray,Color.Black)
                                CalcBtn("5", 0, 0,
                                    {
                                        currentVal =
                                            if (currentVal == "0") "5" else currentVal + "5"
                                    }, Color.LightGray,Color.Black)
                                CalcBtn("6", 0, 0,
                                    {
                                        currentVal =
                                            if (currentVal == "0") "6" else currentVal + "6"
                                    }, Color.LightGray,Color.Black)
                            }
                            Row(Modifier) {
                                CalcBtn("1", 0, 0,
                                    {
                                        currentVal =
                                            if (currentVal == "0") "1" else currentVal + "1"
                                    }, Color.LightGray,Color.Black)
                                CalcBtn("2", 0, 0,
                                    {
                                        currentVal =
                                            if (currentVal == "0") "2" else currentVal + "2"
                                    }, Color.LightGray,Color.Black)
                                CalcBtn("3", 0, 0,
                                    {
                                        currentVal =
                                            if (currentVal == "0") "3" else currentVal + "3"
                                    }, Color.LightGray,Color.Black)
                            }
                            Row(Modifier) {
                                CalcBtn("0", 127, 0,
                                    {
                                        currentVal =
                                            if (currentVal == "0") "0" else currentVal + "0"
                                    }, Color.LightGray,Color.Black)
                                CalcBtn(".", 0, 0, { currentVal = currentVal + "." }, Color.LightGray,Color.Black)
                            }
                        }
//                Button + dan =
                        Column(Modifier) {
                            CalcBtn("-", 0, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.SUBTRACT else CalcOperator.SUBTRACT; prevVal = currentVal; currentVal = "0"; mode = "Subtraction"}, Color(0xFFFF9800),Color.White)
                            CalcBtn("+", 0, 0, {operate = if(operate == CalcOperator.IDLE) CalcOperator.ADD else CalcOperator.ADD; prevVal = currentVal; currentVal = "0"; mode = "Addition"}, Color(0xFFFF9800),Color.White)
                            Spacer(Modifier.height(3.dp))
                            Button(
                                modifier = Modifier.padding(end = 10.dp).height(88.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Green,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(5.dp),
                                onClick = {
                                    if(prevVal != ""){
                                        var new_prevVal = prevVal.toDouble()
                                        var new_current = currentVal.toDouble();
                                        currentVal = Calculate(new_prevVal, new_current, operate).toString()
                                        prevVal = ""
                                    }else{
                                        var new_current = currentVal.toDouble();
                                        currentVal = Calculate(new_current, 0.0, operate).toString()
                                        prevVal = ""
                                    }
                                }
                            ) { Text("=", fontWeight = FontWeight.Bold) }
                        }
                    }
                }
                Column(Modifier.padding(top = 5.dp).height(238.dp), verticalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .fillMaxWidth().height(110.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(5.dp),
                        onClick = {
                            if(currentVal.isNotEmpty()){
                                val new_current = currentVal.dropLast(1)

                                currentVal = if (new_current.isEmpty()) "0" else new_current
                            }
                        }
                    ) { Text("C", fontWeight = FontWeight.Bold) }
                    Spacer(Modifier.height(3.dp))
                    Button(
                        modifier = Modifier.height(110.dp)
                            .padding(end = 10.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF9800),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(5.dp),
                        onClick = { currentVal = "0"; prevVal = ""}
                    ) { Text("AC", fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    AppTheme {
        Calculator(Modifier.fillMaxSize())
    }
}