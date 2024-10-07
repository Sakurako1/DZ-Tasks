const BASE_URL = 'http://localhost:8081';

$(function() {
    initializeSidebar();
    bindSidebarEvents();

    loadAllTasks();
});
