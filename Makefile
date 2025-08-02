# Variables
DOCKER_COMPOSE = docker compose

# Colors
GREEN = \033[0;32m
RED = \033[0;31m
BLUE = \033[0;34m
NC = \033[0m

# Commands
run:
	@echo "$(GREEN)Starting the application...$(NC)"
	$(DOCKER_COMPOSE) up -d --build

stop:
	@echo "$(RED)Stopping the application...$(NC)"
	$(DOCKER_COMPOSE) down

logs:
	@echo "$(BLUE)Tailing logs... Press Ctrl+C to stop.$(NC)"
	$(DOCKER_COMPOSE) logs -f

rebuild:
	@echo "$(GREEN)Rebuilding the application...$(NC)"
	$(DOCKER_COMPOSE) down
	$(DOCKER_COMPOSE) up -d --build

clean:
	@echo "$(RED)Cleaning containers and volumes...$(NC)"
	$(DOCKER_COMPOSE) down -v

setup:
	@if [ ! -f .env ]; then \
		echo "$(GREEN)Creating .env file from .env.example...$(NC)"; \
		cp .env.example .env; \
	else \
		echo "$(GREEN).env file already exists.$(NC)"; \
	fi

build:
	@echo "$(GREEN)Building Docker image...$(NC)"
	docker build -t fx-app-image .

mvn-build:
	@echo "$(GREEN)Building project with Maven...$(NC)"
	mvn clean package -DskipTests

test:
	@echo "$(GREEN)Running tests with Maven...$(NC)"
	mvn test

help:
	@echo "$(GREEN)Usage: make <command>$(NC)"
	@echo ""
	@echo "Commands:"
	@echo "  run        - Start the application (with build)"
	@echo "  stop       - Stop the application"
	@echo "  logs       - Show application logs"
	@echo "  rebuild    - Rebuild and restart the application"
	@echo "  clean      - Stop containers and remove volumes"
	@echo "  setup      - Create .env file from .env.example if missing"
	@echo "  build      - Build Docker image only"
	@echo "  mvn-build  - Build app using Maven (outside Docker)"
	@echo "  test       - Run unit tests"
	@echo "  help       - Display this help message"
