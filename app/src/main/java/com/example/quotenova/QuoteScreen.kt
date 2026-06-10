package com.example.quotenova

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuoteScreen() {

    val context = LocalContext.current

    var currentQuote by remember {
        mutableStateOf(
            QuoteData.listOfQuotes.random()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
            .padding(bottom = 100.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "QuoteNova",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111827)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Daily inspiration for your journey",
            color = Color(0xFF6B7280),
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.height(50.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f),

            shape = RoundedCornerShape(24.dp),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1F2937)
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
                        modifier = Modifier.padding(20.dp),
                        fontSize = 24.sp,
                        color = Color(0xFFFBBF24)
                    )

                    Text(
                        text = "- ${quote.author}",
                        modifier = Modifier.padding(
                            start = 100.dp,
                            end = 100.dp,
                            bottom = 20.dp
                        ),
                        fontSize = 18.sp,
                        color = Color(0xFFD1D5DB)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
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
                modifier = Modifier.size(180.dp, 50.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF374151),
                    contentColor = Color(0xFFFBBF24)
                )
            ) {

                Text(
                    text = "Next Quote",
                    fontWeight = FontWeight.SemiBold,
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

                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color(0xFF374151),
                        CircleShape
                    )
            ) {

                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Quote",
                    tint = Color(0xFFFBBF24)
                )
            }
        }
    }
}