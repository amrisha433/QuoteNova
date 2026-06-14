package com.example.quotenova.ui

import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quotenova.QuoteData


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuoteScreen() {

    val context = LocalContext.current

    var isDarkMode by remember {
        mutableStateOf(false)
    }

    val backgroundColor =
        if (isDarkMode) Color(0xFF0F172A)
        else Color(0xFFF8FAFC)

    val cardColor =
        if (isDarkMode) Color(0xFF1E293B)
        else Color(0xFFFFFFFF)

    val titleColor =
        if (isDarkMode) Color.White
        else Color(0xFF111827)

    val subtitleColor =
        if (isDarkMode) Color(0xFF94A3B8)
        else Color(0xFF6B7280)

    val quoteColor =
        if (isDarkMode) Color(0xFF10B981)
        else Color(0xFF0F766E)

    val authorColor =
        if (isDarkMode) Color(0xFFCBD5E1)
        else Color(0xFF4B5563)

    val buttonColor =
        if (isDarkMode) Color(0xFF10B981)
        else Color(0xFF0F766E)

    val shareButtonColor =
        if (isDarkMode) Color(0xFF334155)
        else Color(0xFFE2E8F0)

    var currentQuote by remember{
        mutableStateOf(
            QuoteData.listOfQuotes.random()
        )
    }
    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(backgroundColor)
            .padding(bottom = 100.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {

            IconButton(
                onClick = {
                    isDarkMode = !isDarkMode
                }
            ) {

                Icon(
                    imageVector =
                        if (isDarkMode)
                            Icons.Default.LightMode
                        else
                            Icons.Default.DarkMode,
                    contentDescription = "Theme Toggle",
                    tint = buttonColor
                )
            }
        }

        Text(
            text = "QuoteNova",
            fontSize = 42.sp,
            fontWeight = FontWeight.Companion.Bold,
            color = titleColor
        )
        Spacer(modifier = Modifier.Companion.height(16.dp))
        Text(
            text = "Daily inspiration for your journey",
            color = subtitleColor,
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.Companion.height(50.dp))

        Card(
            modifier = Modifier.Companion
                .fillMaxWidth(0.9f),

            shape = RoundedCornerShape(24.dp),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),

            colors = CardDefaults.cardColors(
                containerColor = cardColor
            )
        ) {

            AnimatedContent(
                targetState = currentQuote,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "Quote Animation"
            ) { quote ->

                Column {

                    Text(
                        text = quote.text,
                        modifier = Modifier.Companion.padding(20.dp),
                        fontSize = 24.sp,
                        color = quoteColor
                    )

                    Text(
                        text = "- ${quote.author}",
                        modifier = Modifier.Companion.padding(
                            start = 100.dp,
                            end = 100.dp,
                            bottom = 20.dp
                        ),
                        fontSize = 18.sp,
                        color = authorColor
                    )
                }
            }
        }

        Spacer(modifier = Modifier.Companion.height(40.dp))

        Row(
            verticalAlignment = Alignment.Companion.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = {

                    val availableQuotes =
                        QuoteData.listOfQuotes.filter {
                            it != currentQuote
                        }

                    if (availableQuotes.isNotEmpty()) {
                        currentQuote = availableQuotes.random()
                    }
                },
                modifier = Modifier.Companion.size(180.dp, 50.dp),

                shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = "Next Quote",
                    fontWeight = FontWeight.Companion.SemiBold,
                    fontSize = 18.sp
                )
            }

            IconButton(
                onClick = {

                    val shareText =
                        "\"${currentQuote.text}\"\n\n- ${currentQuote.author}"

                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, shareText)
                        type = "text/plain"
                    }

                    context.startActivity(
                        Intent.createChooser(
                            shareIntent,
                            "Share Quote"
                        )
                    )
                },

                modifier = Modifier.Companion
                    .size(48.dp)
                    .background(
                        shareButtonColor,
                        CircleShape
                    )
            ) {

                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Quote",
                    tint = buttonColor
                )
            }
        }
    }
}