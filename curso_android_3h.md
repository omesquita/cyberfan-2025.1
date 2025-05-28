# Curso Android 3h: Chat App com Jetpack Compose

## **Hora 1: Setup e Fundamentos (60min)**

### Setup Inicial (15min)
- Criação do projeto no Android Studio
- Configuração do Firebase (Firestore)
- Dependências necessárias
- Estrutura de pastas (MVVM)

### Jetpack Compose Básico (25min)
- Composables fundamentais (Text, Button, TextField, LazyColumn)
- State e recomposição
- Preview e desenvolvimento iterativo
- Tela de entrada: Nome + Chat ID

### MVVM Setup (20min)
- Criação do modelo de dados (Message)
- ViewModel para entrada e chat
- Repository pattern simplificado
- Navegação entre telas

---

## **Hora 2: Desenvolvimento Core (60min)**

### Firebase Integration (25min)
- Configuração do Firestore
- Função para enviar mensagem
- Listener para receber mensagens em tempo real
- Tratamento de erros básico

### UI Implementation (25min)
- Tela de entrada (Nome + Chat ID)
- Lista de mensagens com LazyColumn
- Campo de input para nova mensagem
- Navegação simples entre telas
- Design básico com Material 3

### State Management (10min)
- Gerenciamento de estado no ViewModel
- Flow/StateFlow para dados reativos
- Atualização da UI automaticamente

---

## **Hora 3: Polimento e Features (60min)**

### Coroutines (20min)
- Operações assíncronas
- Lifecycle-aware operations
- Loading states

### Features Avançadas (25min)
- Timestamp nas mensagens
- Scroll automático para última mensagem
- Diferenciação visual (minhas mensagens vs outras)
- Validações básicas

### Testing e Deploy (15min)
- Teste no emulador/dispositivo
- Debug de problemas comuns
- APK generation (se tempo permitir)

---

## **Estrutura de Arquivos Sugerida**

```
app/
├── data/
│   ├── model/
│   │   └── Message.kt
│   └── repository/
│       └── ChatRepository.kt
├── ui/
│   ├── chat/
│   │   ├── ChatScreen.kt
│   │   ├── ChatViewModel.kt
│   │   └── MessageItem.kt
│   ├── entry/
│   │   ├── EntryScreen.kt
│   │   └── EntryViewModel.kt
│   └── navigation/
│       └── AppNavigation.kt
└── MainActivity.kt
```

---

## **Dependências Principais**

```kotlin
// Firebase
implementation 'com.google.firebase:firebase-firestore-ktx:24.9.1'

// Compose
implementation 'androidx.compose.ui:ui:1.5.4'
implementation 'androidx.compose.material3:material3:1.1.2'
implementation 'androidx.activity:activity-compose:1.8.0'

// ViewModel
implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2'

// Navigation (se necessário)
implementation 'androidx.navigation:navigation-compose:2.7.4'
```

---

## **Cronograma Detalhado**

| Tempo | Atividade | Conteúdo |
|-------|-----------|----------|
| 0-15min | Setup | Projeto + Firebase |
| 15-40min | Compose Básico | UI Components |
| 40-60min | MVVM | Arquitetura |
| 60-85min | Firebase | Database Integration |
| 85-110min | UI Completa | Chat Interface |
| 110-130min | Coroutines | Async Operations |
| 130-155min | Polish | Features extras |
| 155-180min | Test & Debug | Finalização |

---

## **Pontos de Checkpoint**

### Checkpoint 1 (60min): 
✅ Projeto criado e rodando
✅ Tela básica com Compose
✅ Estrutura MVVM definida

### Checkpoint 2 (120min):
✅ Firebase conectado
✅ Envio de mensagens funcionando
✅ Lista de mensagens aparecendo

### Checkpoint 3 (180min):
✅ App completo e funcional
✅ Features extras implementadas
✅ Testado e funcionando

---

## **Material de Apoio Necessário**

1. **Repositório Git** com branches para cada etapa
2. **Slides** com conceitos teóricos básicos
3. **Código starter** pré-configurado
4. **Cheat sheet** com Composables mais usados
5. **Troubleshooting guide** para problemas comuns

---

## **Tips para o Instrutor**

- Tenha o projeto completo pronto como backup
- Prepare branches git para cada etapa principal
- Use live coding, mas tenha código pronto para copy/paste
- Foque na prática, teoria mínima necessária
- Incentive perguntas durante o desenvolvimento
- Tenha um projeto Android já configurado no Firebase