import React, {useEffect, useState} from 'react';

const Notification = ({ message, type }: NotificationProps) => {
    const [isVisible, setIsVisible] = useState(true);

    useEffect(() => {
        const timer = setTimeout(() => {
            setIsVisible(false);
        }, 5000);

        return () => clearTimeout(timer);
    }, []);

    if (!isVisible) return null;

    return (
        <div
            style={{
                position: 'fixed',
                bottom: '20px',
                left: '50%',
                transform: 'translateX(-50%)',
                padding: '10px 20px',
                borderRadius: '5px',
                backgroundColor: type === 'success' ? '#4CAF50' : '#f44336',
                color: 'white',
            }}
        >
            {message}
        </div>
    )
};

export default Notification;